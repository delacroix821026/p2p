package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.RabbitConfigration;
import com.newland.financial.p2p.common.handler.RestError;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cendaijuan
 */
@RestController
@RefreshScope
@Log4j
public class Example {
    /***/
    @Value("${form}")
    private String form;

    /**
     * @return String
     */
    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }

    /***/
    @Autowired
    private DiscoveryClient client;

    /**
     * @param a Integer
     * @param b Integer
     * @return Integer
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam final Integer a, @RequestParam final Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        log.debug("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        log.info("p2p_server come in==========");
        /*for (Map.Entry<String, String> entry : exceptionMapping.getExceptionMap().entrySet()) {
            log.info(entry.getKey());
            log.info(entry.getValue());

        }*/
        //throw new BaseRuntimeException("TEST001");
        throw new NullPointerException();
        //return r;
    }

    /**
     * @param a Integer
     * @param b Integer
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/add1", method = RequestMethod.GET)
    public Integer add1(@RequestParam final Integer a, @RequestParam final Integer b) {
        log.info("Server add1!");
        //throw new BaseRuntimeException("2001");
        //throw new Exception("nfaf");
        Thread t = new Thread();
        try {
            t.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 666;
    }

    /***/
   /* @Autowired
    private ExceptionMapping exceptionMapping;*/

    /**
     * 测试2／2路由.
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
    public String sendmsg() {
        RestError.Builder builder = new RestError.Builder();
        builder.setCode("10001");
        builder.setMessage("Testxx");
        log.info("Server sendmsg!");
        rabbitTemplate.convertAndSend(RabbitConfigration.EXCHANGE_NAMEA, "spring.msg", builder.build());
        return "success";
    }

    /**
     * 测试1／2路由.
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/sendmsgB", method = RequestMethod.GET)
    public String sendmsgB() {
        RestError.Builder builder = new RestError.Builder();
        builder.setCode("10002");
        builder.setMessage("TestxxB");
        log.info("Server sendmsgB!");
        rabbitTemplate.convertAndSend(RabbitConfigration.EXCHANGE_NAMEA, "spring.msg.sec", builder.build());
        return "success";
    }

    /**
     * 测试全局路由.
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/sendmsgC", method = RequestMethod.GET)
    public String sendmsgC() {
        RestError.Builder builder = new RestError.Builder();
        builder.setCode("10003");
        builder.setMessage("TestxxC");
        log.info("Server sendmsgC!");
        rabbitTemplate.convertAndSend(RabbitConfigration.EXCHANGE_NAMEB, "", builder.build());
        return "success";
    }

    /**
     * 测试全词匹配路由.
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/sendmsgD", method = RequestMethod.GET)
    public String sendmsgD() {
        RestError.Builder builder = new RestError.Builder();
        builder.setCode("10004");
        builder.setMessage("TestxxD");
        log.info("Server sendmsgD!");
        rabbitTemplate.convertAndSend(RabbitConfigration.EXCHANGE_NAMEC, "spring.tcc", builder.build());
        return "success";
    }

    /**
     * 测试全词匹配路由失败.
     * @return Integer
     * @throws Exception if has error
     */
    @RequestMapping(value = "/sendmsgE", method = RequestMethod.GET)
    public String sendmsgE() {
        RestError.Builder builder = new RestError.Builder();
        builder.setCode("10005");
        builder.setMessage("TestxxE");
        log.info("Server sendmsgE!");
        rabbitTemplate.convertAndSend(RabbitConfigration.EXCHANGE_NAMEC, "spring.tcc1", builder.build());
        return "success";
    }

    /***/
    @Autowired
    private RabbitTemplate rabbitTemplate;
}