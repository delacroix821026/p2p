package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.exception.BaseException;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.domain.entity.InstallObjectFactory;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.IMerchantService;
import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.IRefundService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款Controller.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/refund")
public class RefundController {
    /**
     * 注入service.
     */
    @Autowired
    private IOrderService orderService;
    /**
     * service注入.
     */
    @Autowired
    private IMerchantService merchantService;
    /***/
    @Autowired
    private IRefundService refundService;
    /**
     * 生产退款请求报文.
     */
    @RequestMapping(value = "/getRefundMsg", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public RefundMsgReq createRefundOrder(@RequestBody String jsonStr) {
        log.info("jsonStr" + jsonStr);
        JSONObject param = JSON.parseObject(jsonStr);
        String orderId = param.getString("orderId");
        OrderInfo orderInfo = orderService.findOrderInfo(orderId);
        if (orderInfo == null) {
            throw new BaseRuntimeException("2004");
        } else if (orderInfo.getMerchantId() == null) {
            throw new BaseRuntimeException("2005");
        }
        MerInfo merInfo = merchantService.getMerchantDetail(orderInfo.getMerchantId());
        RefundMsgReq refundMsgReq = InstallObjectFactory.installRefundMsgReq(orderInfo, merInfo);
        if (refundMsgReq != null) {
            log.info("封装的退款请求报文：" + refundMsgReq.toString());
        }
        return refundMsgReq;
    }


    /**
     * 根据乐百分返回的信息，更新退款单信息后返回前端.
     *

     */
    @RequestMapping(value = "/updateRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateRefundOrder(@RequestBody Refund refund) {
        Refund re = refund;
       return refundService.insertOrUpdateRefund(re);
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
