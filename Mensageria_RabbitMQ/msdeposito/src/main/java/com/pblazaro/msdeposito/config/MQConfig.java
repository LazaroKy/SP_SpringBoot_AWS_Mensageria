package com.pblazaro.msdeposito.config;

import org.hibernate.boot.model.internal.QueryBinder;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

        @Value("${mq.queues.transacao-banco}") // queue(fila) - principal
        private String transacaoBanco;
        private String exchangeTransacao = "exchange-transacao-banco";
        private String deadLetterQueueDeposito = "DLQ-transacao-banco";
        private String deadLetterExchangeTransacao = "DLX-transacao-banco";
        private String deadLetterRoutingKey = "routingKey.DL";

        @Bean
        public DirectExchange exchangeTransacao(){
                return new DirectExchange(exchangeTransacao, true, false);
        }

        @Bean
        public Queue queueTransacaoBanco(){
                Map<String, Object> arguments = new HashMap<>();
                arguments.put("x-message-ttl", 20000);
                arguments.put("x-dead-letter-exchange", deadLetterExchangeTransacao);
                arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);

            return new Queue( transacaoBanco, true, false, false, arguments);
        }

        @Bean
        public DirectExchange exchangeTransacaoBancoDLX(){
                return new DirectExchange(deadLetterExchangeTransacao, true, false);
        }

        @Bean
        public Queue queueTransacaoBancoDLQ(){
                return new Queue(deadLetterQueueDeposito, true, false, false);
        }

        @Bean
        public Binding relacaoDeadLetter(){
               return BindingBuilder
                        .bind(queueTransacaoBancoDLQ())
                        .to(exchangeTransacaoBancoDLX())
                        .with(deadLetterRoutingKey);
        }


}
