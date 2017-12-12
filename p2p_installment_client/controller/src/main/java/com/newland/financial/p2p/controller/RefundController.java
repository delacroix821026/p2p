
package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.IRefundService;
import com.newland.financial.p2p.service.ISendService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;


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
    /**
     * 生成退款单.
     * @param jsonStr 包含订单号
     * @return
     */
    @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createRefundOrder(@RequestBody String jsonStr) {
        log.info(jsonStr);
        Map<String, String> map = new HashMap<String, String>();
        RefundMsgReq refundMsgReq = refundService.getRefundMsg(jsonStr);
        if (refundMsgReq == null) {
            map.put("respCode", "0420");
            map.put("respMsg", "订单已经超过45天，无法退款");
            return map;
        }
        log.info("client中拿到的refundMsgReq:" + refundMsgReq.toString());
        Refund refund = sendService.sendRefundMsgReq(refundMsgReq);
        refundService.insertRefund(refund);
        return refund;
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

