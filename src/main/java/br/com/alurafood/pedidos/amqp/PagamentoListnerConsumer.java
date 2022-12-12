package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.constant.ApiConstantRequestId;
import br.com.alurafood.pedidos.constant.ApiConstantVersion;
import br.com.alurafood.pedidos.dto.PagamentoDTO;
import br.com.alurafood.pedidos.exceptions.BadRequestExceptions;
import br.com.alurafood.pedidos.model.Observabilidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

// consumidor da fila pagamento.concluido
@Component
public class PagamentoListnerConsumer {

    private final Logger logger = LoggerFactory.getLogger(PagamentoListnerConsumer.class);
    private static final String tagStart = "Inicio do processo do evento, class: PagamentoListner, ";
    private static final String recebeMessage = "Recebendo o evento de pagamento, ";

    // O RabbitListener é a anotação responsável por consumir dados de uma fila e o nome da fila
    // está declarado corretamente.

    // @Payload, É a anotação que informa que o parâmetro vai receber o corpo da mensagem. Observação: não é obrigatório
    // quando tem apenas um parâmetro.

    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMessage(@Payload PagamentoDTO pagamentoDTO) {

        String correlationId = UUID.randomUUID().toString();

        Observabilidade observabilidade = new Observabilidade(ApiConstantVersion.versionApiOne, ApiConstantRequestId.requestIdAluraFood, recebeMessage,  correlationId);

        logger.info(String.format(tagStart + observabilidade));

        System.out.println("-------------------");
        System.out.println(pagamentoDTO.toString());
        // verificar o evento e aplicar regras de negocio

        if (Objects.equals(pagamentoDTO.getNumero(), "0000")) {
            throw new BadRequestExceptions("Error, codigo do cartão invalido, " + pagamentoDTO.getNumero());
        }
    }
}
