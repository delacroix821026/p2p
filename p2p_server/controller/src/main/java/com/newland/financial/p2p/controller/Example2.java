package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * aaaa.
 */
@Controller
@RefreshScope
@Log4j
public class Example2 {
    /***/
    @Autowired
    private DiscoveryClient client;

    /**
     *
     * @param a dsf
     * @param b fd
     * @return fda
     */
    @RequestMapping(value = "/addtest", method = RequestMethod.GET)
    public String add(@RequestParam final Integer a, @RequestParam final Integer b) {
        Integer r = a + b;
        log.info("p2p_server come in controller==========");
        return "www.baidu.com";
    }
}
