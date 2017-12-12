package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import lombok.extern.log4j.Log4j;
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
        return r;
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
        throw new BaseRuntimeException("2001");
        //throw new Exception("nfaf");
        //return r;
    }

    /***/
   /* @Autowired
    private ExceptionMapping exceptionMapping;*/
}