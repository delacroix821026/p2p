package com.newland.financial.p2p;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author cendaijuan
 */
@Configuration
public class RabbitConfigration {
    /**
     * Jackson2JsonMessageConverter.
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /***/
    public static final String EXCHANGE_NAMEA = "catalogExchange";

    /***/
    public static final String EXCHANGE_NAMEB = "catalogExchangeb";

    /***/
    public static final String EXCHANGE_NAMEC = "catalogExchangec";

    /**
     * 绑定RabbitTemplate.
     * @param connectionFactory ConnectionFactory
     * @param jackson2JsonMessageConverter Jackson2JsonMessageConverter
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter);
        return template;
    }

}
