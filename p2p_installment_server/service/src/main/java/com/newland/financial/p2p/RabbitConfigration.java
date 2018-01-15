package com.newland.financial.p2p;

import com.newland.financial.p2p.service.impl.Receiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.PlatformTransactionManager;


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
    public static final String QUEUENAME = "spring-boot";

    /***/
    public static final String QUEUE_NAME_B = "spring-log";

    /***/
    public static final String QUEUE_NAME_C = "spring-c";

    /***/
    public static final String QUEUE_NAME_D = "spring-d";

    /***/
    public static final String EXCHANGE_NAMEA = "catalogExchange";

    /***/
    public static final String EXCHANGE_NAMEB = "catalogExchangeb";

    /***/
    public static final String EXCHANGE_NAMEC = "catalogExchangec";

    @Bean
    @Scope("prototype")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               PlatformTransactionManager transactionManager,
                                                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        //factory.setChannelTransacted(true);
        //factory.setTransactionManager(transactionManager);
        //factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(3);
        return factory;
    }

    /**
     * 绑定Binding.
     * @param connectionFactory ConnectionFactory
     * @param listenerAdapter MessageListenerAdapter
     * @return SimpleMessageListenerContainer

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUENAME);
        container.setMessageListener(listenerAdapter);
        container.setConcurrentConsumers(3);
        return container;
    }

    /**
     * 绑定Binding.
     * @param receiver Receiver
     * @param jackson2JsonMessageConverter Jackson2JsonMessageConverter
     * @return MessageListenerAdapter

    @Bean("listenerAdapter")
    MessageListenerAdapter listenerAdapter(Receiver receiver, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, jackson2JsonMessageConverter);
        messageListenerAdapter.setDefaultListenerMethod("receiveErrorMessage");
        return messageListenerAdapter;
    }
     */
}
