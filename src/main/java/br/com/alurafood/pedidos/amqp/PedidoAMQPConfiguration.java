package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoAMQPConfiguration {

    // Conversor Jackson2JsonMessageConverter

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // configuração para envio objeto json RabbitTemplate

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;

    }

    // criar a fila e redirecionar caso der erros para exchage deadDlq

    @Bean
    public Queue filaDetalhesPedidos() {
        return QueueBuilder
                .nonDurable("pagamentos.detalhes-pedido")
                .deadLetterExchange("pagamento.dlx")
                .build();
    }

    // referencia exchange Fanaut

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("pagamento.ex").build();
    }

    // ligar a fila a exchage

    @Bean
    public Binding bindingPagamentoPedido(FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(filaDetalhesPedidos()).to(fanoutExchange);
    }

    //  --------------------------  criar a fila dlq  -------------------------------------------------------

    @Bean
    public Queue filaDlqDetalhesPedidos() {
        return QueueBuilder.nonDurable("pagamentos.detalhes-pedido-dlq")
                .build();
    }

    //  ------------------criar Exange DLQ para gerenciar o envio a fila dlq de mensagens --------------------

    @Bean
    public FanoutExchange deadDlqExchange() {
        return ExchangeBuilder.fanoutExchange("pagamento.dlx").build();
    }

    //  ------------------- ligar a fila dlq a exchage dlx  -------------------------------------------

    @Bean
    public Binding bindingDlqPagamentoPedido() {
        return BindingBuilder.bind(filaDlqDetalhesPedidos()).to(deadDlqExchange());
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connect) {
        return new RabbitAdmin(connect);
    }

    // inicializar RabbitAdmin / disparar evento interface

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
