package com.newland.financial.p2p.controller;


import com.newland.financial.p2p.service.ExampleService;
import com.newland.financial.p2p.service.FeignService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Object addWS(HttpServletRequest request) {
        log.info("ws controller add Enter:::::::::::::" + request.getRequestedSessionId());
        //HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        log.info("ws controller add Enter:::::::::::::" + session.getAttribute("abc"));
        return feignService.add(5, 18);
    }

    @RequestMapping(value = "/addwith/{paramA}/{paramB}", method = RequestMethod.GET)
    public Object urlPath(@PathVariable(name="paramA") Integer paramA, @PathVariable(name="paramB") Integer paramB) {
        log.info("controller add Enter: A:" + paramA);
        log.info("controller add Enter: B:" + paramB);
        return feignService.add(5, 18);
    }
}