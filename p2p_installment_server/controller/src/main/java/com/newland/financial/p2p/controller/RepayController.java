package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *还款推送
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/RepayController")
public class RepayController {

    /**
     *接收还款推送信息
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receiveRepayInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object receiveRepayInfo(@RequestBody String jsonStr){


        return null;
    }

    /**
     *更新还款单信息
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRepayInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateRepayInfo(@RequestBody String jsonStr){


        return null;
    }
}
