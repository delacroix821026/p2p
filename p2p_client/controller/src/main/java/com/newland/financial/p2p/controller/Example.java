package com.newland.financial.p2p.controller;


import com.newland.financial.p2p.service.ExampleService;
import com.newland.financial.p2p.service.FeignService;
import com.newland.financial.p2p.service.Impl.FeignFallbackFactory;
import feign.Client;
import feign.Contract;
import feign.Request;
import feign.Retryer;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import feign.codec.Decoder;
import feign.codec.Encoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RefreshScope
@Log
@Import(FeignClientsConfiguration.class)
public class Example {
    @Autowired
    public Example(Decoder decoder, Encoder encoder,Client client, Contract contract, @Value("${DEVLOPER_NAME}") String devlopName) {

        feignService = HystrixFeign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .client(client)
                .options(new Request.Options(13 * 1000, 3 * 1000))
                .retryer(new Retryer.Default(100, 1000, 1))
                .target(FeignService.class, "http://p2p-server" + devlopName, (FallbackFactory<? extends FeignService>) new FeignFallbackFactory());
    }
    @Value("${form}")
    private String form;

    @Autowired
    private ExampleService exampleService;

    /*@Autowired*/
    private FeignService feignService;

    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add() {
        //log.info("controller add Enter=====" + UserInfoUtils.getUserInfo().getLoginName());
        //throw new NullPointerException();
        return feignService.add0(6,9);
    }

    @RequestMapping(value = "/add_ws", method = RequestMethod.GET)
    public Object addWS(HttpServletRequest request) {
        log.info("ws controller add Enter:::::::::::::" + request.getRequestedSessionId());
        //HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        log.info("ws controller add Enter:::::::::::::" + session.getAttribute("abc"));

        return feignService.add0(5, 18);
    }

    @RequestMapping(value = "/addwith/{paramA}/{paramB}", method = RequestMethod.GET)
    public Object urlPath(@PathVariable(name="paramA") Integer paramA, @PathVariable(name="paramB") Integer paramB) {
        log.info("controller add Enter: A:" + paramA);
        log.info("controller add Enter: B:" + paramB);
        return feignService.add0(5, 18);
    }

    @RequestMapping(value = "/add1", method = RequestMethod.GET)
    public Object addC1() {
        log.info("controller add Enter=====");
        return feignService.add2(9,9);
    }
}