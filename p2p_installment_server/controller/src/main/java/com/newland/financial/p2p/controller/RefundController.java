package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款Controller.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/refundController")
public class RefundController {

    /**
     * 创建退款单.
     *
     * @param jsonStr 接收的json字符串:<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号"<BR>
     *                }<BR>
     * @return 返回jsonStr :<BR>
     * {<BR>
     * &nbsp;&nbsp;"version":"版本号(1.0.0)",<BR>
     * &nbsp;&nbsp;"encoding":"编码方式",<BR>
     * &nbsp;&nbsp;"certId":"证书ID",<BR>
     * &nbsp;&nbsp;"txnType":"交易类型(04：退款)",<BR>
     * &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     * &nbsp;&nbsp;"merId":"商户代码",<BR>
     * &nbsp;&nbsp;"merPwd":"商户密码",<BR>
     * &nbsp;&nbsp;"merName":"商户名称",<BR>
     * &nbsp;&nbsp;"merAbbr":"商户简称",<BR>
     * &nbsp;&nbsp;"contractsCode":"合同号",<BR>
     * &nbsp;&nbsp;"backUrl":"异步通知地址",<BR>
     * &nbsp;&nbsp;"signature":"签名"<BR>
     * }<BR>
     */
    @RequestMapping(value = "/createRefundOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object createRefundOrder(@RequestBody String jsonStr) {


        return null;
    }


    /**
     * 根据乐百分返回的信息，更新退款单信息后返回前端.
     *
     * @param jsonStr 申请退款后乐百分返回的json<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"version":"版本号(1.0.0)",<BR>
     *                &nbsp;&nbsp;"encoding":"编码方式",<BR>
     *                &nbsp;&nbsp;"certId":"证书ID",<BR>
     *                &nbsp;&nbsp;"txnType":"交易类型(04：退款)",<BR>
     *                &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"merPwd":"商户密码",<BR>
     *                &nbsp;&nbsp;"merName":"商户名称",<BR>
     *                &nbsp;&nbsp;"merAbbr":"商户简称",<BR>
     *                &nbsp;&nbsp;"signature":"签名",<BR>
     *                &nbsp;&nbsp;"state":"退款状态:1:退款成功,2:人工审核,0:退款失败",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号",<BR>
     *                &nbsp;&nbsp;"contractsCode":"合同号",<BR>
     *                &nbsp;&nbsp;"respCode":"响应码",<BR>
     *                &nbsp;&nbsp;"respMsg":"响应信息",<BR>
     *                &nbsp;&nbsp;"respTime":"响应时间",<BR>
     *                &nbsp;&nbsp;"queryId":"请求编号",<BR>
     *                &nbsp;&nbsp;"signature":"签名"<BR>
     *                }<BR>
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;&nbsp;"redundId":"退款单号",<BR>
     * &nbsp;&nbsp;"merName":"商户名称",<BR>
     * &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     * &nbsp;&nbsp;"cancelAmount":"商户退款金额",<BR>
     */
    @RequestMapping(value = "/updateRefundOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateRefundOrder(@RequestBody String jsonStr) {


        return null;
    }

    /**
     * 接收乐百分推送的退款信息.
     *
     * @param jsonStr 退款信息<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"version":"版本号(1.0.0)",<BR>
     *                &nbsp;&nbsp;"encoding":"编码方式",<BR>
     *                &nbsp;&nbsp;"certId":"证书ID",<BR>
     *                &nbsp;&nbsp;"txnType":"交易类型(04：退款)",<BR>
     *                &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"merPwd":"商户密码",<BR>
     *                &nbsp;&nbsp;"merName":"商户名称",<BR>
     *                &nbsp;&nbsp;"merAbbr":"商户简称",<BR>
     *                &nbsp;&nbsp;"signature":"签名",<BR>
     *                &nbsp;&nbsp;"state":"退款状态:1:退款成功,2:人工审核,0:退款失败",<BR>
     *                &nbsp;&nbsp;"orderId":"订单编号",<BR>
     *                &nbsp;&nbsp;"contractsCode":"合同号",<BR>
     *                &nbsp;&nbsp;"respCode":"响应码",<BR>
     *                &nbsp;&nbsp;"respMsg":"响应信息",<BR>
     *                &nbsp;&nbsp;"respTime":"响应时间",<BR>
     *                &nbsp;&nbsp;"queryId":"请求编号",<BR>
     *                &nbsp;&nbsp;"signature":"签名"<BR>
     *                }<BR>
     * @return 返回参数:success
     */
    @RequestMapping(value = "/receiveRefundInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object receiveRefundInfo(@RequestBody String jsonStr) {


        return null;
    }
}
