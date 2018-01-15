package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.RabbitConfigration;
import com.newland.financial.p2p.common.handler.RestError;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author Delacroix
 */
@Component
public class Receiver {
    /****/
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     *
     * @param message String
     */
    public void receiveMessage(String message) {
        latch.countDown();
        System.out.println("Received <" + message + ">, count:" + latch.getCount());
        Thread t = new Thread();
        try {
            t.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param restError RestError
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfigration.QUEUENAME , durable = "true") ,
            exchange = @Exchange(value = RabbitConfigration.EXCHANGE_NAMEA , type = "topic" , durable = "true") ,
            key = "spring.#"),
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveErrorMessage(RestError restError) {
        latch.countDown();
        System.out.println("Received A <" + restError.getMessage() + ">, count:" + restError.getCode());
        Thread t = new Thread();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * *一级单词路由
     * @param restError RestError
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfigration.QUEUE_NAME_B , durable = "true") ,
            exchange = @Exchange(value = RabbitConfigration.EXCHANGE_NAMEA , type = "topic" , durable = "true") ,
            key = "spring.*"),
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveErrorMessageB(RestError restError) {
        latch.countDown();
        System.out.println("Received B <" + restError.getMessage() + ">, count:" + restError.getCode());
        Thread t = new Thread();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * #群发
     * @param restError RestError
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfigration.QUEUE_NAME_C , durable = "true") ,
            exchange = @Exchange(value = RabbitConfigration.EXCHANGE_NAMEB , type = ExchangeTypes.FANOUT, durable = "true")),
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveErrorMessageC(RestError restError) {
        latch.countDown();
        System.out.println("Received C <" + restError.getMessage() + ">, count:" + restError.getCode());
        Thread t = new Thread();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * #群发
     * @param restError RestError
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfigration.QUEUE_NAME_D , durable = "true") ,
            exchange = @Exchange(value = RabbitConfigration.EXCHANGE_NAMEB , type = ExchangeTypes.FANOUT, durable = "true")),
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveErrorMessageD(RestError restError) {
        latch.countDown();
        System.out.println("Received D <" + restError.getMessage() + ">, count:" + restError.getCode());
        Thread t = new Thread();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * #群发
     * @param restError RestError
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfigration.QUEUENAME , durable = "true") ,
            exchange = @Exchange(value = RabbitConfigration.EXCHANGE_NAMEC , type = ExchangeTypes.DIRECT, durable = "true"),
            key = "spring.tcc"),
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveErrorMessageE(RestError restError) {
        latch.countDown();
        System.out.println("Received E <" + restError.getMessage() + ">, count:" + restError.getCode());
        Thread t = new Thread();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return CountDownLatch
     */
    public CountDownLatch getLatch() {
        return latch;
    }
}
