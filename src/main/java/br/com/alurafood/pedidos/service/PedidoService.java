package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.model.Observabilidade;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    private final static String errorMessageNotFound = "Error: recurso não encontrado!, ";

    private final Logger logger = LoggerFactory.getLogger(PedidoService.class);
    private final static String tag = "class: PedidoService, ";

    @Transactional(readOnly = true)
    public Page<PedidoDto> obterTodos(Observabilidade observabilidade, Pageable paginacao) {

        logger.info(String.format(tag + observabilidade));

        return repository.findAll(paginacao)
                .map(p -> modelMapper.map(p, PedidoDto.class));
    }
    @Transactional(readOnly = true)
    public PedidoDto obterPorId(Long id, Observabilidade observabilidade) {

        logger.info(String.format(tag + observabilidade));

        Optional<Pedido> pedido = repository.findById(id);

        if (pedido.isEmpty()) {
            logger.error(String.format(errorMessageNotFound + tag + observabilidade));
            throw new EntityNotFoundException(errorMessageNotFound);
        }

        return modelMapper.map(pedido, PedidoDto.class);
    }
    @Transactional
    public PedidoDto criarPedido(PedidoDto dto, Observabilidade observabilidade) {

        logger.info(String.format(tag + observabilidade));

        Pedido pedido = modelMapper.map(dto, Pedido.class);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = repository.save(pedido);

        return modelMapper.map(salvo, PedidoDto.class);
    }
    @Transactional
    public PedidoDto atualizaStatus(Long id, StatusDto dto, Observabilidade observabilidade) {

        logger.info(String.format(tag + observabilidade));

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            logger.error(String.format(errorMessageNotFound + tag + observabilidade));
            throw new EntityNotFoundException(errorMessageNotFound);
        }

        pedido.setStatus(dto.getStatus());
        repository.atualizaStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }
    @Transactional
    public void aprovaPagamentoPedido(Long id, Observabilidade observabilidade) {

        logger.info(String.format(tag + observabilidade));

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            logger.error(String.format(errorMessageNotFound + tag + observabilidade));
            throw new EntityNotFoundException(errorMessageNotFound);
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }
}
