package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.InstallObjectFactory;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.domain.entity.OrderMsgReq;
import com.newland.financial.p2p.domain.entity.OrderQueryReq;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
     *  @param orderId 订单号
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
        log.info("========tradeUpdateOrder========jsonStr:" + jsonStr);
        OrderInfo order = orderService.findOrderInfo(orderId);
        String contractsCode = order.getContractsCode();
        log.info("constractscode====:" + contractsCode);
        if (order.getContractsCode() != null) {
            log.info("已成交的商户不可重复扫码更改信息");
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
     * @param ob 订单信息
     * @param orderId 订单号
     * @return true or false
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateOrderInfo(@RequestBody OrderInfo ob, @PathVariable(name = "orderId") String orderId) {
        OrderInfo or = ob;
        return orderService.updateOrderInfo(or);
    }

    /**
     * 生成一张空白订单.
     * @param merchantId 商户代码
     * @param jsonStr 订单信息.
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
     * @param orderId orderId
     * @return 订单信息.
     */
    @RequestMapping(value = "/{merchantId}/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object findOrderInfo(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId) {
        log.info("======3:come to server:findOrderInfo=====");
        log.info("orderId:" + orderId + ";merchantId:" + merchantId);
        OrderInfo orderInfo = orderService.findOrderInfoPos(orderId, merchantId);
        if (orderInfo == null) {
            return null;
        }
        MerInfo merInfo = orderService.findMerInfo(orderInfo);
        OrderQueryReq oq = InstallObjectFactory.installOrderQueryReq(orderInfo, merInfo);
        log.info("4:OrderQueryReq:" + oq.toString());
        return oq;
    }

    /**
     * 更新订单并过去最新订单信息.
     *@param orderId 订单号
     * @param or 订单信息
     * @return 最新的订单信息
     */
    @RequestMapping(value = "/{orderId}/OrderInfo", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateAndGetOrder(@RequestBody OrderInfo or, @PathVariable(name = "orderId") String orderId) {
        log.info("======查询后更新：come to server:updateAndGetOrder=====");
        log.info("=========传入参数========:" + or);
        OrderInfo orderInfo = or;
        orderService.updateOrderInfo(orderInfo);
        OrderInfo orf = orderService.findOrderInfo(orderInfo.getOrderId());
        orf.setRespCode(orderInfo.getRespCode());
        orf.setRespMsg(orderInfo.getRespMsg());
        orf.setMerName(orderInfo.getMerName());
        return orf;
    }

    /**
     * 查询空白订单信息.
     *
     * @param orderId orderId
     * @return 订单信息.
     */
    @RequestMapping(value = "/{orderId}/OrderInfo", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object findBlankOrder(@PathVariable(name = "orderId") String orderId) {
        log.info("======come to server:findBlankOrder=====");
        log.info("orderId:" + orderId);
        OrderInfo orderInfo = orderService.findOrderInfo(orderId);
        Map<String, Object> map = new HashMap<String, Object>();
        long createtime  = orderInfo.getCreateTime().getTime();
        long endtime = new Date().getTime();
        if (endtime - createtime > 86400000) {
            log.info("二维码已超过一天有效期endtime - createtime=" + (endtime - createtime));
            map.put("respCode", "0411");
            map.put("respMsg", "二维码已过期失效");
            return map;
        }
        map.put("orderInfo", orderInfo);
        map.put("respCode", "0000");
        map.put("respMsg", "二维码可用");
        return map;
    }

    /**
     * 用户订单列表详细.
     *
     * @return OrderInfolist
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoDetailByCustomer (@RequestBody PageModel<OrderInfo> pageModel) {
        log.info("*********---------***********");
        log.info("getMerchantList:" + pageModel.getModel().getOpenId());
        log.info("getMerchantList:" + pageModel.getModel().getOrderId());
        log.info("getMerchantList:" + pageModel.getPageNum());
        log.info("getMerchantList:" + pageModel.getPageSize());

        return orderService.getOrderInfoDetailByCustomer(pageModel);
    }

    /**
     * 商户查询订单列表.
     *
     * @return OrderInfolist
     */
    @RequestMapping(value = "/merchant/{merchantId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody String jsonStr) {
        log.info("========***********************=======");
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        log.info("merchantId:" + paramJSON.getString("merchantId"));
        log.info("orderId:" + paramJSON.getString("orderId"));
        log.info("accName:" + paramJSON.getString("accName"));
        log.info("statusA:" + paramJSON.getString("statusA"));
        log.info("statusB:" + paramJSON.getString("statusB"));
        log.info("statusC:" + paramJSON.getString("statusC"));
        log.info("beginTime:" + paramJSON.getString("beginTime"));
        log.info("endTime:" + paramJSON.getString("endTime"));
        log.info("pageSize:" + paramJSON.getString("pageSize"));
        log.info("pageNum:" + paramJSON.getString("pageNum"));

        return orderService.getOrderInfoListByMerchant(merchantId,jsonStr);
    }

    /**
     * 商户查询订单列表.
     *
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
     * @return OrderInfolist
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public  Object getOrderInfoListByPlantManager(@RequestBody PageModel<OrderInfo> pageModel) {
        log.info("*********----管理员查询订单---***********");
        log.info("getMerchantList:" + pageModel.getModel());
        log.info("getMerchantId:" + pageModel.getModel().getMerchantId());
        log.info("getOrderId:" + pageModel.getModel().getOrderId());
        log.info("getMerName:" + pageModel.getModel().getMerName());
        log.info("getContractsState:" + pageModel.getModel().getContractsState());
        log.info("getPageNum:" + pageModel.getPageNum());
        log.info("getPageSize:" + pageModel.getPageSize());
        return orderService.getOrderInfoListByPlantManager(pageModel);
    }
    /**
     * 平台管理员查询订单列表.
     *
     * @return OrderInfolist
     */
    @RequestMapping(value = "/plant/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OrderInfo getOrderInfoDetailByPlantManager(@PathVariable(name = "orderId") String orderId) {
        return null;
    }

}
