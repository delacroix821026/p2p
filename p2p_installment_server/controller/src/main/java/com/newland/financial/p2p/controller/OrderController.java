package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.*;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 订单处理Controller.
 *
 * @author Mxia
 */

@RestController
@Log4j
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单.
     *
     * @param jsonStr 请求参数：<BR>
     *                {<BR>
     *                &nbsp;"orderId":"123123",//订单号<BR>
     *                &nbsp;"txnterms":6;,//分期数<BR>
     *                &nbsp;"accName":"jack",//持卡人姓名<BR>
     *                &nbsp;"accNo":"12345678",//信用卡账号<BR>
     *                &nbsp;"accIdcard":"11111111111",//持卡人身份证号<BR>
     *                &nbsp;"accMobile":"1300000000",//持卡人银行预留手机号码<BR>
     *                &nbsp;"cvn2":"567",//信用卡背面末三位数字<BR>
     *                &nbsp;"validity":"0820",//信用卡有效期<BR>
     *                &nbsp;"smsCode":"111111"//验证码<BR>
     *                }
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;"merName":"新大陆",//商户名称<BR>
     * &nbsp;"orderInfo":{<BR>
     * &nbsp;&nbsp;"orderId":"12345",//商户订单号<BR>
     * &nbsp;&nbsp;"txnTime":150000000000,//交易时间<BR>
     * &nbsp;&nbsp;"txnAmt":2000000;,//分期金额（单位分）<BR>
     * &nbsp;&nbsp;"txnterms":6,//分期数<BR>
     * &nbsp;&nbsp;"amount":30000,//首期金额（单位分）<BR>
     * &nbsp;}<BR>
     * }
     */
    @RequestMapping(value = "/tradeUpdateOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Object tradeUpdateOrder(@RequestBody String jsonStr) {
        log.info("======come to server:tradeUpdateOrder=====");
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String smsCode = paramJSON.getString("smsCode");
        OrderInfo orderInfo = (OrderInfo) orderService.tradeUpdateOrder(jsonStr);
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderMsgReq omr = InstallObjectFactory.installOrderMsgReq(orderInfo, merInfo, smsCode);
        return omr;
    }

    /**
     * 根据乐百分返回的数据更新订单信息.
     *
     * @param ob
     * @return
     */
    @RequestMapping(value = "/updateOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateOrderInfo(@RequestBody OrderInfo ob) {
        OrderInfo or = ob;
        return orderService.updateOrderInfo(or);
    }

    /**
     * 生成一张空白订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单的订单编号
     */
    @RequestMapping(value = "/createBlankOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public String createOrderInfo(@RequestBody String jsonStr) {
        log.info("======come to server:createBlankOrder=====");
        return orderService.createBlankOrder(jsonStr);
    }

    /**
     * 获得相应订单信息.
     *
     * @param jsonStr orderId
     * @return 订单信息.
     */
    @RequestMapping(value = "/findOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public Object findOrderInfo(@RequestBody String jsonStr) {
        log.info("======come to server:findOrderInfo=====");
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderId = paramJSON.getString("orderId");
        OrderInfo orderInfo = orderService.findOrderInfo(orderId);
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderQueryReq oq = InstallObjectFactory.installOrderQueryReq(orderInfo, merInfo);
        log.info("OrderQueryReq:" + oq.toString());
        return oq;
    }

    /**
     *
     */
    @RequestMapping(value = "/updateAndGetOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateAndGetOrder(@RequestBody OrderInfo or) {
        log.info("======come to server:updateAndGetOrder=====");
        log.info(or);
        OrderInfo orderInfo = or;
        orderService.updateOrderInfo(or);
        OrderInfo orf = orderService.findOrderInfo(or.getOrderId());
        orf.setRespCode(or.getRespCode());
        orf.setRespMsg(or.getRespMsg());
        orf.setMerName(or.getMerName());
        return orf;
    }

}
