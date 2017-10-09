package com.newland.financial.p2p.controller;


import com.newland.financial.p2p.service.ExampleService;
import com.newland.financial.p2p.service.FeignService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Log
public class Example {
    @Value("${form}")
    private String form;

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private FeignService feignService;

    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add() {
        log.info("controller add Enter");
        return exampleService.add("");
    }


    @RequestMapping(value = "/add_ws", method = RequestMethod.GET)
    public Object addWS() {
        log.info("ws controller add Enter");
        return feignService.add(5, 18);
    }
}