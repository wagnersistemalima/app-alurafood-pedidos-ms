package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.mapper.ObservabilidadeMapper;
import br.com.alurafood.pedidos.model.Observabilidade;
import br.com.alurafood.pedidos.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private ObservabilidadeMapper observabilidadeMapper;

    private final static String listarPedidos = "listar todos os pedidos";
    private final static String detalharPedido = "detalhar pedido por id";
    private final static String cadastrarPedido = "realizar pedido";
    private final static String atualizarStatusPedido = "atualizar status do pedido";
    private final static String aprovarPagamento = "aprovar pagamento";
    private final static String tagStart = "Inicio do processo, class: PedidoController, ";
    private final static String tagEnd = "Fim do processo, class: PedidoController, ";

    private final Logger logger = LoggerFactory.getLogger(PedidoController.class);


    @GetMapping()
    public ResponseEntity<Page<PedidoDto>> listarTodos(
            @RequestHeader("Accept-Version") @NotEmpty(message = "informe o cabeçalho") String version,
            @RequestHeader("x-requestId") @NotEmpty(message = "informe o cabeçalho") String requestId,
            @PageableDefault(size = 10) Pageable paginacao
    ) {
        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, listarPedidos, correlationId);

        logger.info(String.format(tagStart + observabilidade));

        Page<PedidoDto> response = service.obterTodos(observabilidade, paginacao);

        logger.info(String.format(tagEnd + observabilidade));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> listarPorId(
            @RequestHeader("Accept-Version") @NotEmpty(message = "informe o cabeçalho") String version,
            @RequestHeader("x-requestId") @NotEmpty(message = "informe o cabeçalho") String requestId,
            @PathVariable @NotNull Long id) {

        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, detalharPedido, correlationId);

        logger.info(String.format(tagStart + observabilidade));

        PedidoDto dto = service.obterPorId(id, observabilidade);

        logger.info(String.format(tagEnd + observabilidade));

        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<PedidoDto> realizaPedido(
            @RequestHeader("Accept-Version") @NotEmpty(message = "informe o cabeçalho") String version,
            @RequestHeader("x-requestId") @NotEmpty(message = "informe o cabeçalho") String requestId,
            @RequestBody @Valid PedidoDto dto, UriComponentsBuilder uriBuilder) {

        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, cadastrarPedido, correlationId);

        logger.info(String.format(tagStart + observabilidade));

        PedidoDto pedidoRealizado = service.criarPedido(dto, observabilidade);

        URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

        logger.info(String.format(tagEnd + observabilidade));

        return ResponseEntity.created(endereco).body(pedidoRealizado);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDto> atualizaStatus(
            @RequestHeader("Accept-Version") @NotEmpty(message = "informe o cabeçalho") String version,
            @RequestHeader("x-requestId") @NotEmpty(message = "informe o cabeçalho") String requestId,
            @PathVariable Long id, @RequestBody StatusDto status) {

        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, atualizarStatusPedido, correlationId);

        logger.info(String.format(tagStart + observabilidade));

        PedidoDto dto = service.atualizaStatus(id, status, observabilidade);

        logger.info(String.format(tagEnd + observabilidade));

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovaPagamento(
            @RequestHeader("Accept-Version") @NotEmpty(message = "informe o cabeçalho") String version,
            @RequestHeader("x-requestId") @NotEmpty(message = "informe o cabeçalho") String requestId,
            @PathVariable @NotNull Long id) {

        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, aprovarPagamento, correlationId);

        logger.info(String.format(tagStart + observabilidade));

        service.aprovaPagamentoPedido(id, observabilidade);

        logger.info(String.format(tagEnd + observabilidade));

        return ResponseEntity.ok().build();

    }
}
