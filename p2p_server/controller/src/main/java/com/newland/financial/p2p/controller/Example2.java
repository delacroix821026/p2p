package com.newland.financial.p2p.controller;

//import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * aaaa.
 */
@RestController
@RefreshScope
@Log4j
@RequestMapping("/example")
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
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Integer add(@RequestParam final Integer a, @RequestParam final Integer b) {
        Integer r = a + b;
        log.info("p2p_server come in controller==========");
        return r;
    }

    /**
     *
     * @param a dsf
     * @param b fd
     * @return fda
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer update(@RequestParam final Integer a, @RequestParam final Integer b) {
        Integer r = a + b;
        log.info("p2p_server come in controller==========");
        //throw new BaseRuntimeException("Test100");
        return r;
    }

}
