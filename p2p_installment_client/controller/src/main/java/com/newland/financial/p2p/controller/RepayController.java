package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IEgwService;
import com.newland.financial.p2p.service.IInnerService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *还款推送处理.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/RepayController")
public class RepayController {
    /**内部服务.*/
    @Autowired
    private IInnerService innerService;
    /*** 外发接口.*/
    @Autowired
    private IEgwService egwService;

    @RequestMapping(value = "/receiveRepaymentInfo", method = RequestMethod.POST)
    public String receiveRepaymentInfo(@RequestBody String jsonStr) {
        log.info("------------------------------receiveRepaymentInfo----------------------------");
        log.info("jsonStr：" + jsonStr);
        //调用Ifpay-client验签
        String respStus = egwService.signature(jsonStr);
        //判断验签结果，成功则调用server更新还款表HttpServletRequest request
        if("true".equals(respStus)){
            log.info("------------------------验签成功，更新还款表信息表");
           String repayResp =  innerService.updateRepayInfo(jsonStr);

        }
        //更新完成后应答“success”
        return "success";
//        return null;
    }



}
