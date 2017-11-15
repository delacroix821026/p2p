package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *订单处理Controller.
 * @author Gregory
 */

@Controller
@Log4j
@RequestMapping("/OrderController")
public class OrderController {


    /**
     *创建订单.
     * @param jsonStr 请求参数：<BR>
     * {<BR>
     * &nbsp;"merId":"001",//商户代码<BR>
     * &nbsp;"txnAmt":20000,//订单总金额<BR>
     * &nbsp;"txnterms":6;,//分期数<BR>
     * &nbsp;"accName":"jack",//持卡人姓名<BR>
     * &nbsp;"accNo":"12345678",//信用卡账号<BR>
     * &nbsp;"accIdcard":"11111111111",//持卡人身份证号<BR>
     * &nbsp;"accMobile":"1300000000",//持卡人银行预留手机号码<BR>
     * &nbsp;"CVN2":"567",//信用卡背面末三位数字<BR>
     * &nbsp;"validity":"0820",//信用卡有效期<BR>
     * &nbsp;"smsCode":"111111"//验证码<BR>
     * }
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
    @ResponseBody
    @RequestMapping(value = "/createOrder",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object createOrder(@RequestBody String jsonStr){


        return null;
    }

    /**
     *根据乐百分返回的数据更新订单信息.
     * @param jsonStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateOrderInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateOrderInfo(@RequestBody String jsonStr){


        return null;
    }

    /**
     *查询订单.
     * @param jsonStr <BR>
     * {<BR>
     * &nbsp;"merId":"001",//商户代码<BR>
     * &nbsp;"state":"1",//状态0未结清，1已结清，2退款<BR>
     * &nbsp;"orderId":"110";,//订单号<BR>
     * &nbsp;"accName":"jack",//持卡人姓名<BR>
     * &nbsp;"bigTime":"2017-01-02",//开始时间<BR>
     * &nbsp;"endTime":"2017-01-03",//结束时间<BR>
     * &nbsp;"page":1,//当前页<BR>
     * &nbsp;"count":5//显示条数<BR>
     * }
     * @return 返回参数:<BR>
     * {<BR>
     * &nbsp;"orderList":[<BR>
     * &nbsp;&nbsp;{<BR>
     * &nbsp;&nbsp;&nbsp;"orderId":"12345",//商户订单号<BR>
     * &nbsp;&nbsp;&nbsp;"txnTime":150000000000,//交易时间<BR>
     * &nbsp;&nbsp;&nbsp;"accName":"jack",//持卡人姓名<BR>
     * &nbsp;&nbsp;&nbsp;"txnAmt":20000,//原交易金额(应还款金额)(单位分)<BR>
     * &nbsp;&nbsp;&nbsp;"txnterms":6;,//分期数<BR>
     * &nbsp;&nbsp;&nbsp;"sumAmount":500000,//已还款总金额(单位分)<BR>
     * &nbsp;&nbsp;&nbsp;"sumTerms":2,//已还期数<BR>
     * &nbsp;&nbsp;&nbsp;"contractsState":"1",//状态<BR>
     *&nbsp;&nbsp; },<BR>
     * &nbsp;&nbsp;{}....<BR>
     * &nbsp;]<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrderInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryOrderInfo(@RequestBody String jsonStr){


        return null;
    }
}
