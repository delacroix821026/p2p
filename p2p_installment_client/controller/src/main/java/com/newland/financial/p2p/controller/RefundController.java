/*
package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

*/
/**
 *退款Controller
 * @author Gregory
 *//*

@RestController
@Log4j
@RequestMapping("/refund")
public class RefundController {

    */
/**
     * 创建退款单.
     *
     * @param jsonStr 接收的json字符串:<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号"<BR>
     *                }<BR>
     *
     * @return 返回jsonStr :<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号"<BR>
     *                &nbsp;&nbsp;"redundId":"退款单号",<BR>
     *                &nbsp;&nbsp;"merName":"商户名称",<BR>
     *                &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     *                &nbsp;&nbsp;"cancelAmount":"商户退款金额",<BR>
     *                &nbsp;&nbsp;"state":"退款状态:1:退款成功,2:人工审核,0:退款失败",<BR>
     *                &nbsp;&nbsp;"respCode":"响应码",<BR>
     *                &nbsp;&nbsp;"respMsg":"响应信息",<BR>
     *                &nbsp;&nbsp;"respTime":"响应时间"<BR>
     *                }<BR>
     *//*

    @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createRefundOrder(@RequestBody String jsonStr) {

        return null;
    }

    */
/**
     * 上传凭证.
     *
     * @param jsonStr 接收的json字符串:<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号"<BR>
     *                }<BR>
     *
     *//*

    @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestBody String jsonStr) {

    }
}
*/
