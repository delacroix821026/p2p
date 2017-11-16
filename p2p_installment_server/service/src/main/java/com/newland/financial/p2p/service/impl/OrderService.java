package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 订单处理ServiceImpl.
 *
 * @author Gregory, Mxia
 */
@Log4j
@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderInfoDao orderInfoDao;

    /**
     * 创建一个空白订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单编号
     */
    public Object createBlankOrder(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(UUID.randomUUID().toString().replaceAll("-", "")); //主键
        //生成订单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Random r = new Random();
        String str = new DecimalFormat("00").format(r.nextInt(100));
        Date date = new Date();
        StringBuffer s = new StringBuffer(sdf.format(date));
        s.append(str);
        orderInfo.setOrderId(new String(s));
        orderInfo.setCreateTime(date);
        orderInfo.setMerId(paramJSON.getString("merId"));
        orderInfo.setTxnAmt(paramJSON.getLong("txnAmt"));
        boolean b = orderInfoDao.insertOrder(orderInfo);
        return b == true ? new String(s) : false;
    }

    /**
     * 获取相应订单信息.
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    public OrderInfo findOrderInfo(String orderId) {
        //判断orderId是否为空
        if (orderId == null || orderId.length() == 0) {
            return null;
        }
        return orderInfoDao.selectOrderInfo(orderId);
    }

}
