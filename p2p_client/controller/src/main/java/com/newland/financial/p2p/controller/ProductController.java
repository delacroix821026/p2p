package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.FeignService;
import com.newland.financial.p2p.service.IProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cendaijuan
 * */
@RestController
@Log
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/GetProduct/{proId}", method = RequestMethod.POST)
    public Object getProduct(@PathVariable(name="proId") String proId, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +proId);
        return productService.getProduct(jsonStr);
    }

    @RequestMapping(value = "/InsertProduct/{proId}", method = RequestMethod.POST)
    public Object insertProduct(@PathVariable(name="proId") String proId, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +proId);
        return productService.insertProduct(jsonStr);
    }

    @RequestMapping(value = "/PutOrDown/{proId}", method = RequestMethod.POST)
    public Object putOrDown(@PathVariable(name="proId") String proId, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +proId);
        return productService.putOrDown(jsonStr);
    }
}
