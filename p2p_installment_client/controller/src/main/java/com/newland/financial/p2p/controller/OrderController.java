package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.ISendService;
import com.newland.financial.p2p.utils.UserInfoUtils;
import lombok.extern.java.Log;
import org.ohuyo.libra.client.session.LibraSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gregory
 */
@RestController
@Log
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISendService sendService;

    /**
     * 生成一张空白订单.
     *
     * @param jsonStr 请求参数:<BR>
     * {<BR>
     *&nbsp;"merId":"111111111",//商户代码<BR>
     *&nbsp;"txnAmt":10000//分期金额<BR>
     *}
     * @return 返回参数:<BR>
     *{<BR>
     *&nbsp;"orderId":2017111615064349906//订单号<BR>
     *}
     */
    @RequestMapping(value = "/createBlankOrder", method = RequestMethod.POST)
    public String createBlankOrder(@RequestBody String jsonStr) {
        log.info("========client:createBlankOrder=======");
        log.info("jsonStr：" + jsonStr);
        return orderService.createOrderInfo(jsonStr);
    }

    /**
     * 查询单个订单.
     *
     * @param jsonStr 请求参数:<BR>
     *{<BR>
     *&nbsp;"orderId":2017111615064349906//订单号<BR>
     *}
     * @return 返回参数:<BR>
     *{<BR>
     *&nbsp;"id": "0a4e6a51c15a463fadb1f6daf5a3f74c",<BR>
     *&nbsp;"txnTime": null,//交易时间<BR>
     *&nbsp;"merId": "111111111",//商户代码<BR>
     *&nbsp;"txnAmt": 10000,//交易总额<BR>
     *&nbsp;"txnterms": null,//分期数<BR>
     *&nbsp;"orderId": "2017111615064349906",//订单号<BR>
     *&nbsp;"accName": "Mxia",//持卡人名称<BR>
     *&nbsp;"accNo": "12345678",//持卡人信用卡账号<BR>
     *&nbsp;"validity": "0822",//卡有效期<BR>
     *&nbsp;"accIdcard": "12345678",//持卡人身份证<BR>
     *&nbsp;"accMobile": "111111",//持卡人银行预留手机号<BR>
     *&nbsp;"cvn2": "123",//银行卡背面3位数<BR>
     *&nbsp;"discount": null,<BR>
     *&nbsp;"contractsCode": "12121212",//合同号<BR>
     *&nbsp;"amount": 1666,//首期还款金额<BR>
     *&nbsp;"queryId": null,<BR>
     *&nbsp;"poundage": null,//手续费<BR>
     *&nbsp;"contractsState": "1",//合同状态0：未生效 1：已生效 2：已结束(正常还款) 3：已结束(断供) 4：已结束(提前还款) 5：已结束(退款)<BR>
     *&nbsp;"sumTerms": "1",//已还款总期数<BR>
     *&nbsp;"sumAmount": 1666,//已还款总金额<BR>
     *&nbsp;"remainAmount": 7000,//剩余还款金额<BR>
     *&nbsp;"cancelAmount": 7000,//商户退款金额<BR>
     *&nbsp;"cancelInterest": null,//商户退款利息<BR>
     *&nbsp;"valueAdded": null,<BR>
     *&nbsp;"createTime": 1510816003000,//创建时间<BR>
     *&nbsp;"stus": null,//退款状态：0退款失败，1退款成功，2人工审核，无值或者null时表示订单没有退款行为.<BR>
     *&nbsp;"merName": "新大陆"//商户名称<BR>
     *&nbsp;"respCode": "0000"//返回码，0000表示操作成功<BR>
     *&nbsp;"respMsg": "查询成功"//返回信息<BR>
     *}
     */
    @RequestMapping(value = "/findOrderInfo", method = RequestMethod.POST)
    public Object findOrderInfo(@RequestBody String jsonStr) {
        log.info("========1:client:findOrderInfo=======");
        log.info("2:jsonStr：" + jsonStr);
        Object ob = orderService.findOrderInfo(jsonStr);
        Object ob1 = sendService.sendOrderQueryMsg(ob);
        return orderService.updateAndGetOrder(ob1);
    }

    /**
     * 填写订单进行交易.
     * @param jsonStr 请求参数：<BR>
     * {<BR>
     * &nbsp;"orderId":"123123",//订单号<BR>
     * &nbsp;"txnterms":6;,//分期数<BR>
     * &nbsp;"accName":"jack",//持卡人姓名<BR>
     * &nbsp;"accNo":"12345678",//信用卡账号<BR>
     * &nbsp;"accIdcard":"11111111111",//持卡人身份证号<BR>
     * &nbsp;"accMobile":"1300000000",//持卡人银行预留手机号码<BR>
     * &nbsp;"cvn2":"567",//信用卡背面末三位数字<BR>
     * &nbsp;"validity":"0820",//信用卡有效期<BR>
     * &nbsp;"smsCode":"111111"//短信验证码<BR>
     * }
     * @return 返回参数：<BR>
     *{<BR>
     *&nbsp;"id":"0a4e6a51c15a463fadb1f6daf5a3f74c",<BR>
     *&nbsp;"txnTime":null,//交易时间<BR>
     *&nbsp;"merId":"111111111",//商户代码<BR>
     *&nbsp;"txnAmt":10000,//交易总额<BR>
     *&nbsp;"txnterms":null,//分期数<BR>
     *&nbsp;"orderId":"2017111615064349906",//订单号<BR>
     *&nbsp;"accName":"Mxia",//持卡人名称<BR>
     *&nbsp;"accNo":"12345678",//持卡人信用卡账号<BR>
     *&nbsp;"validity":"0822",//卡有效期<BR>
     *&nbsp;"accIdcard":"12345678",//持卡人身份证<BR>
     *&nbsp;"accMobile":"111111",//持卡人银行预留手机号<BR>
     *&nbsp;"cvn2":"123",//银行卡背面3位数<BR>
     *&nbsp;"discount":null,<BR>
     *&nbsp;"contractsCode":"12121212",//合同号<BR>
     *&nbsp;"amount":1666,//首期还款金额<BR>
     *&nbsp;"queryId":null,<BR>
     *&nbsp;"poundage":null,//手续费<BR>
     *&nbsp;"contractsState":"1",//合同状态0：未生效 1：已生效 2：已结束(正常还款) 3：已结束(断供) 4：已结束(提前还款) 5：已结束(退款)<BR>
     *&nbsp;"sumTerms":"1",//已还款总期数<BR>
     *&nbsp;"sumAmount":1666,//已还款总金额<BR>
     *&nbsp;"remainAmount":7000,//剩余还款金额<BR>
     *&nbsp;"cancelAmount":7000,//商户退款金额<BR>
     *&nbsp;"cancelInterest":null,//商户退款利息<BR>
     *&nbsp;"valueAdded":null,<BR>
     *&nbsp;"createTime":1510816003000,//创建时间<BR>
     *&nbsp;"stus":null,//退款状态：0退款失败，1退款成功，2人工审核，无值或者null时表示订单没有退款行为.<BR>
     *&nbsp;"merName":"新大陆"//商户名称<BR>
     *&nbsp;"respCode":"0000"//返回码，0000表示操作成功<BR>
     *&nbsp;"respMsg":""//返回信息<BR>
     *}
     */
    @RequestMapping(value = "/tradeUpdateOrder", method = RequestMethod.POST)
    public Object tradeUpdateOrder(@RequestBody String jsonStr) {
        log.info("========client:tradeUpdateOrder=======");
        log.info("1.jsonStr：" + jsonStr);
        LibraSession librea = UserInfoUtils.getUserInfo();
        Object ob = orderService.tradeUpdateOrder(jsonStr);
        Object obj = sendService.sendOrderMsgToLbf(ob);
        log.info("=====obj=====:"+obj);
        orderService.updateOrderInfo(obj);
        return obj;
    }

}
