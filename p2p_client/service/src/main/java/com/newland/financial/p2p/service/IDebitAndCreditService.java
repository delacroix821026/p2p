package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.DebitAndCreditServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-server${DEVLOPER_NAME:}", fallback = DebitAndCreditServiceHystrix.class)
public interface IDebitAndCreditService {
    @RequestMapping(method = RequestMethod.POST, value = "/DebitAndCreditController/getDebitList")
    Object getDebitList(@RequestBody String jsonStr);
}
