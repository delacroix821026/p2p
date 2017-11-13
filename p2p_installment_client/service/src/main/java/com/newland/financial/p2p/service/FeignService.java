package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.FeignServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "p2p-server${DEVLOPER_NAME:}", fallback = FeignServiceHystrix.class)
/*
@FeignClient(name = "local",url = "localhost:8769", fallback = FeignServiceHystrix.class)
public interface FeignService {
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

    @RequestMapping(method = RequestMethod.GET, value = "/addtest")
    Integer add1(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add2(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
*/

@FeignClient(name = "p2p",url = "localhost:8080/TomcatTest", fallback = FeignServiceHystrix.class)
public interface FeignService {
    @RequestMapping(method = RequestMethod.GET, value = "/HelloServlet")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

}
