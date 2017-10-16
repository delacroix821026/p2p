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

    @RequestMapping(value = "/GetProduct/{paramA}", method = RequestMethod.POST)
    public Object getProduct(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.getProduct(jsonStr);
    }

    @RequestMapping(value = "/InsertProduct/{paramA}", method = RequestMethod.POST)
    public Object insertProduct(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.insertProduct(jsonStr);
    }

    @RequestMapping(value = "/PutOrDown/{paramA}", method = RequestMethod.POST)
    public Object putOrDown(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.putOrDown(jsonStr);
    }

    @RequestMapping(value = "/UpdateProd/{proId}", method = RequestMethod.POST)
    public Object updateProd(@PathVariable(name="proId") String proId, @RequestBody String jsonStr){
        log.info("controller add Enter: proId:" +proId);
        return productService.updateProd(jsonStr);
    }

    @RequestMapping(value = "/GetProdList/{count}/{page}", method = RequestMethod.POST)
    public Object updateProd(@PathVariable(name="count") String count,@PathVariable(name="page") String page,
                             @RequestBody String jsonStr){
        log.info("controller add Enter: count:" +count+"controller add Enter: page:" +page);
        return productService.getProdList(jsonStr);
    }
}
