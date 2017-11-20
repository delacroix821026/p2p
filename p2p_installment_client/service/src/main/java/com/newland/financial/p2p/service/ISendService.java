package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.SendServiceHystrix;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "p2p",url = "localhost:3003", fallback = SendServiceHystrix.class)
public interface ISendService {
    @RequestMapping(method = RequestMethod.POST, value = "/ybforder/sendOrderMsg")
    Object sendOrderMsgToLbf(@RequestBody Object object);

    @RequestMapping(method = RequestMethod.POST, value = "/ybforder/sendOrderQueryMsg")
    Object sendOrderQueryMsg(@RequestBody Object object);
}
