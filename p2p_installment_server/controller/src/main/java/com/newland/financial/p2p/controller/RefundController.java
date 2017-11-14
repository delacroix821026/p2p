package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *退款Controller
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/RefundController")
public class RefundController {


    /**
     *创建退款单
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createRefundOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object createRefundOrder(@RequestBody String jsonStr){


        return null;
    }

    /**
     *更新退款单信息
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRefundOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateRefundOrder(@RequestBody String jsonStr){


        return null;
    }

    /**
     *接收乐百分推送的退款信息
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receiveRefundInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object receiveRefundInfo(@RequestBody String jsonStr){


        return null;
    }
}
