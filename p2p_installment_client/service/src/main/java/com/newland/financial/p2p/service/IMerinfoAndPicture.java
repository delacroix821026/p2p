package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.MerchantServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://139.196.141.163:8586", name = "p2p", fallbackFactory = MerchantServiceFallBackFactory.class)
public interface IMerinfoAndPicture {
    @RequestMapping(method = RequestMethod.POST, value = "/eprmadp1/lepercent.json")
    String getMerInfoAndPicture(@RequestBody String jsonStr);
}
