package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *订单处理Controller
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/OrderController")
public class OrderController {


    /**
     *创建订单
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object createOrder(@RequestBody String jsonStr){


        return null;
    }

    /**
     *根据乐百分返回的数据更新订单信息
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateOrderInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateOrderInfo(@RequestBody String jsonStr){


        return null;
    }

    /**
     *查询订单
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrderInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryOrderInfo(@RequestBody String jsonStr){


        return null;
    }
}
