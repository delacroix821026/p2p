package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.ProductServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-server${DEVLOPER_NAME:}", fallback = ProductServiceHystrix.class)
public interface IProductService {
    @RequestMapping(method = RequestMethod.POST, value = "/ProductController/GetProduct")
    Object getProduct(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/ProductController/InsertProduct")
    Object insertProduct(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/ProductController/PutOrDown")
    Object putOrDown(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/ProductController/UpdateProd")
    Object updateProd(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/ProductController/GetProdList")
    Object getProdList(@RequestBody String jsonStr);
}
