
package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.IRefundService;
import com.newland.financial.p2p.service.ISendService;
import com.newland.financial.p2p.service.ISignatureIfqService;
import com.newland.financial.p2p.util.NewMerInfoUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *退款Controller
 * @author Mxia
 */

@RestController
@Log4j
@RequestMapping("/refund")
public class RefundController {
    /**注入service.*/
    @Autowired
    private IRefundService refundService;
    /**注入service.*/
    @Autowired
    private ISendService sendService;
    /*** 外发接口.*/
    @Autowired
    private ISignatureIfqService signatureService;
    /**
     * 生成退款单.
     * @param jsonStr 包含订单号
     * @return
     */
    @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createRefundOrder(@RequestBody String jsonStr) {
        log.info(jsonStr);
        RefundMsgReq refundMsgReq = refundService.getRefundMsg(jsonStr);
        if (refundMsgReq == null) {
            throw new BaseRuntimeException("2003");
        }
        log.info("client中拿到的refundMsgReq:" + refundMsgReq.toString());
        Refund refund = sendService.sendRefundMsgReq(refundMsgReq);
        refundService.insertRefund(refund);
        return refund;
    }

    /**
     * 退款推送接口.
     * @param jsonStr 推送内容
     * @return success or failed
     */
    @RequestMapping(value = "/receiveRefund", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String receiveRefund(@RequestBody String jsonStr) {
        log.info("------------------------------receiveRefund----------------------------");
        log.info("jsonStr：" + jsonStr);
        //调用Ifpay-client验签
        String respStus = signatureService.signature(jsonStr);
        //判断验签结果，成功则调用server更新还款表HttpServletRequest request
        if ("true".equals(respStus)) {
            log.info("------------------------验签成功，更新退款信息");
            Boolean repayResp = (Boolean) refundService.insertRefund(NewMerInfoUtils.getNewRefund(jsonStr));
            if (repayResp) {
                //更新完成后应答“success”
                log.info("------------------------更新还款推送信息成功");
                return "success";
            }
        } else {
            log.info("------------------------验签失败！！");
        }
        return "failed";
    }
/**
     * 上传凭证.
     *
     * @param jsonStr 接收的json字符串:<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号"<BR>
     *                }<BR>
     *
     */

   /* @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestBody String jsonStr) {

    }*/
}

