package com.newland.financial.p2p.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.newland.financial.p2p.service.Impl.SendServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "p2p",url = "localhost:3003", fallbackFactory = SendServiceFallBackFactory.class)
public interface ISendService {
    @RequestMapping(method = RequestMethod.POST, value = "/ybforder/sendOrderMsg")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", value = "15000")
    })
    Object sendOrderMsgToLbf(@RequestBody Object object);

    @RequestMapping(method = RequestMethod.POST, value = "/ybforder/sendOrderQueryMsg")
    Object sendOrderQueryMsg(@RequestBody Object object);
}
