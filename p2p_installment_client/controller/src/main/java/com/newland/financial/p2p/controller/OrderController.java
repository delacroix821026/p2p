package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.ISendService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gregory
 */
@RestController
@Log
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISendService sendService;

    /**
     * 短信验证码接口.
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/sendmsg", method = RequestMethod.POST)
    public Object sendMsg(@RequestBody String jsonStr) {
        log.info("========send msg=======");
        log.info("jsonStr：" + jsonStr);

        return sendService.sendMsgToLbf(jsonStr);
    }

    /**
     * 生成一张空白订单.
     * @param jsonStr
     * @return 空白订单的订单编号
     */
    @RequestMapping(value = "/createBlankOrder", method = RequestMethod.POST)
    public String createBlankOrder(@RequestBody String jsonStr) {
        log.info("========client:createBlankOrder=======");
        log.info("jsonStr：" + jsonStr);
        return orderService.createOrderInfo(jsonStr);
    }

    /**
     * 获取指定订单.
     * @param jsonStr orderId 订单编号
     * @return 订单信息
     */
    @RequestMapping(value = "/findOrderInfo", method = RequestMethod.POST)
    public Object findOrderInfo(@RequestBody String jsonStr) {
        log.info("========client:findOrderInfo=======");
        log.info("jsonStr：" + jsonStr);
        return orderService.findOrderInfo(jsonStr);
    }

}
