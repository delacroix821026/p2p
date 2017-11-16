package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *订单处理Controller.
 * @author Gregory
 */

@Controller
@Log4j
@RequestMapping("/ybforder")
public class OrderController {
    @ResponseBody
    @RequestMapping(value = "/sendOrderMsg",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryOrderInfo(@RequestBody String jsonStr){


        return null;
    }
}
