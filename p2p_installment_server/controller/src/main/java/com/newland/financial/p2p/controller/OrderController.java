package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.InstallObjectFactory;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.domain.entity.OrderMsgReq;
import com.newland.financial.p2p.domain.entity.OrderQueryReq;
import com.newland.financial.p2p.service.IMerchantService;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 订单处理Controller.
 *
 * @author Mxia
 */

@RestController
@Log4j
@RequestMapping("/order")
public class OrderController {
    /**
     * 注入对象.
     */
    @Autowired
    private IOrderService orderService;
    /**
     * 注入对象.
     */
    @Autowired
    private IMerchantService merchantService;

    /**
     * 分期交易时进行更新订单.
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
     * @param orderId 订单号
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
    @RequestMapping(value = "/{orderId}/OrderMsgReq", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Object tradeUpdateOrder(@RequestBody String jsonStr, @PathVariable(name = "orderId") String orderId) {
        log.info("======come to server:tradeUpdateOrder=====");
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderid = paramJSON.getString("orderId");
        log.info("========tradeUpdateOrder========jsonStr:" + jsonStr);
        if (orderid == null || "".equals(orderid.trim())) {
            log.info("===Exception:2451===");
            return null;
        }
        OrderInfo order = orderService.findOrderInfo(orderid);
        if (order == null) {
            log.info("===Exception:2453===");
            return null;
        }
        String contractsCode = order.getContractsCode();
        log.info("constractscode====:" + contractsCode);
        if (order.getContractsCode() != null) { //判断订单是否已经经过交易
            log.info("===Exception:2450===");
            return null;
        }
        String smsCode = paramJSON.getString("smsCode");
        OrderInfo orderInfo = (OrderInfo) orderService.tradeUpdateOrder(jsonStr);
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderMsgReq omr = InstallObjectFactory.installOrderMsgReq(orderInfo, merInfo, smsCode);
        return omr;
    }

    /**
     * 根据乐百分返回的数据更新订单信息.
     *
     * @param ob      订单信息
     * @param orderId 订单号
     * @return true or false
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateOrderInfo(@RequestBody OrderInfo ob, @PathVariable(name = "orderId") String orderId) {
        OrderInfo or = ob;
        or.setTotleAmount(or.getTxnAmt() + or.getPoundage());
        return orderService.updateOrderInfo(or);
    }

    /**
     * 生成一张空白订单.
     *
     * @param merchantId 商户代码
     * @param jsonStr    订单信息.
     * @return 空白订单的订单编号
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrderInfo(@RequestBody String jsonStr, @PathVariable(name = "merchantId") String merchantId) {
        log.info("======come to server:createBlankOrder=====jsonStr:" + jsonStr);
        return orderService.createBlankOrder(jsonStr);
    }

    /**
     * 获得相应订单信息.
     *
     * @param orderId    orderId
     * @param merchantId orderId
     * @return 订单信息.
     */
    @RequestMapping(value = "/{merchantId}/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object findOrderInfo(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId) {
        log.info("===come to server:findOrderInfo===");
        log.info("orderId:" + orderId + ";merchantId:" + merchantId);
        OrderInfo orderInfo = orderService.findOrderInfoPos(orderId, merchantId);
        if (orderInfo == null) {
            log.info("===Exception:2453===");
            throw new BaseRuntimeException("2453");
        }
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderQueryReq oq = InstallObjectFactory.installOrderQueryReq(orderInfo, merInfo);
        log.info("===OrderQueryReq:===" + oq.toString());
        return oq;
    }

    /**
     * 更新订单并获去最新订单信息.
     *
     * @param orderId 订单号
     * @param or      订单信息
     * @return 最新的订单信息
     */
    @RequestMapping(value = "/{orderId}/OrderInfo", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateAndGetOrder(@RequestBody OrderInfo or, @PathVariable(name = "orderId") String orderId) {
        log.info("======查询后更新：come to server:updateAndGetOrder=====");
        log.info("=========传入参数========:" + or);
        OrderInfo orderInfo = or;
        orderService.updateOrderInfo(orderInfo);
        OrderInfo orf = orderService.getOrderInfoByManager(orderInfo.getOrderId());
        orf.setRespCode(orderInfo.getRespCode());
        orf.setRespMsg(orderInfo.getRespMsg());
        orf.setMerName(orderInfo.getMerName());
        return orf;
    }

    /**
     * 查询空白订单信息.
     *
     * @param orderId orderId.
     * @return 订单信息.
     */
    @RequestMapping(value = "/{orderId}/OrderInfo", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object findBlankOrder(@PathVariable(name = "orderId") String orderId) {
        log.info("======come to server:findBlankOrder=====");
        log.info("orderId:" + orderId);
        OrderInfo orderInfo = orderService.findOrderInfo(orderId);
        if (orderInfo == null) {
            log.info("===Exception:2004===");
            throw new BaseRuntimeException("2004");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        long createtime = orderInfo.getCreateTime().getTime();
        long endtime = new Date().getTime();
        if (endtime - createtime > 1800000) {
            log.info("二维码已超过30分钟endtime - createtime=" + (endtime - createtime));
            log.info("===Exception:2411===");
            throw new BaseRuntimeException("2411");
        }
        String merechantId = orderInfo.getMerchantId();
        MerInfo merInfo = merchantService.getMerchantDetail(merechantId);
        map.put("orderInfo", orderInfo);
        return InstallObjectFactory.getBlankOrder(map, merInfo);
    }

    /**
     * 用户订单列表.
     *
     * @param jsonStr 查询参数
     * @return Object
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoListByCustomer(@RequestBody String jsonStr) {
        log.info("*********----getOrderInfoListByCustomer----***********");
        log.info("jsonStr = " + jsonStr);
        return orderService.getOrderInfoListByCustomer(jsonStr);
    }

    /**
     * 用户订单详情.
     *
     * @param openId  用户id
     * @param orderId 订单id
     * @return OrderInfolist
     */
    @RequestMapping(value = "/weixin/{openId}/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoDetailByCustomer(@PathVariable(name = "openId") String openId, @PathVariable(name = "orderId") String orderId) {
        log.info("openId:" + openId + ";orderId:" + orderId);
        OrderInfo orderInfo = orderService.findOrderInfoWeiXin(openId, orderId);
        if (orderInfo == null) {
            return null;
        }
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderQueryReq oq = InstallObjectFactory.installOrderQueryReq(orderInfo, merInfo);
        log.info("OrderQueryReq:" + oq.toString());
        return oq;
    }

    /**
     * 商户查询订单列表.
     *
     * @param merchantId
     * @param jsonStr
     * @return OrderInfolist
     */
    @RequestMapping(value = "/{merchantId}/orderList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody String jsonStr) {
        log.info("========***********************=======");
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        log.info("merchantId:" + merchantId);
        log.info("orderId:" + paramJSON.getString("orderId"));
        log.info("accName:" + paramJSON.getString("accName"));
        log.info("status:" + paramJSON.getString("status"));
        log.info("beginTime:" + paramJSON.getString("beginTime"));
        log.info("endTime:" + paramJSON.getString("endTime"));
        log.info("pageSize:" + paramJSON.getString("pageSize"));
        log.info("pageNum:" + paramJSON.getString("pageNum"));

        return orderService.getOrderInfoListByMerchant(merchantId, jsonStr);
    }

    /**
     * 商户查询订单列表.
     *
     * @param merchantId
     * @param orderId
     * @return OrderInfolist
     */
    @RequestMapping(value = "/merchant/{merchantId}/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OrderInfo getOrderInfoDetailByMerchant(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId) {
        return null;
    }

    /**
     * 平台管理员查询订单列表.
     *
     * @param jsonStr
     * @return OrderInfolist
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoListByPlantManager(@RequestBody String jsonStr) {
        log.info("===管理员查询订单===");
        log.info("jsonStr = " + jsonStr);
        return orderService.getOrderInfoListByPlantManager(jsonStr);
    }

    /**
     * 平台管理员查询订单详情.
     *
     * @param orderId
     * @return Object
     */
    @RequestMapping(value = "/plant/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoDetailByPlantManager(@PathVariable(name = "orderId") String orderId) {
        log.info("orderId = " + orderId);
        OrderInfo orderInfo = orderService.findOrderInfoManager(orderId);
        if (orderInfo == null) {
            log.info("===Exception:2453===");
            throw new BaseRuntimeException("2453");
        }
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderQueryReq oq = InstallObjectFactory.installOrderQueryReq(orderInfo, merInfo);
        log.info("OrderQueryReq:" + oq.toString());
        return oq;
    }

    /**
     * 平台管理员查询退款订单.
     *
     * @param jsonStr .
     * @return List<OrderInfo>
     */
    @RequestMapping(value = "/orderRundList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderRundListByPlantManager(@RequestBody String jsonStr) {
        log.info("===getOrderRundListByPlantManager===");
        log.info("jsonStr = " + jsonStr);
        return orderService.getOrderRundListByPlantManager(jsonStr);
    }
    /**
     *  excel导出
     *
     * @param jsonStr .
     */
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void exportOrderInfoExcel(@RequestBody String jsonStr) {
        log.info("===exportOrderInfoExcel===");
        log.info("jsonStr = " + jsonStr);
        orderService.exportOrderInfoExcel(jsonStr);
    }

}
