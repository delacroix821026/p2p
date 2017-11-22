package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IRepayService;
import com.newland.financial.p2p.service.ISignatureIfqService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 还款推送处理.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/repay")
public class RepayController {
    /**
     * 内部服务.
     */
    @Autowired
    private IRepayService repayService;
    /*** 外发接口.*/
    @Autowired
    private ISignatureIfqService signatureService;

    @RequestMapping(value = "/receiveRepaymentInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String receiveRepaymentInfo(@RequestBody String jsonStr) {
        log.info("------------------------------receiveRepaymentInfo----------------------------");
        log.info("jsonStr：" + jsonStr);
        //调用Ifpay-client验签
        String respStus = signatureService.signature(jsonStr);
        //判断验签结果，成功则调用server更新还款表HttpServletRequest request
        if ("true".equals(respStus)) {
            log.info("------------------------验签成功，更新还款信息表");
            String repayResp = repayService.updateRepayInfo(jsonStr);
            if ("true".equals(repayResp)) {
                //更新完成后应答“success”
                log.info("------------------------更新还款推送信息成功");
                return "success";
            }
        } else {
            log.info("------------------------验签失败！！");
        }
        return null;
    }


}
