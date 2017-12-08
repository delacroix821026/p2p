package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    /**注入Dao层对象.*/
    @Autowired
    private IOrderInfoDao orderInfoDao;
    /**注入Dao层对象.*/
    @Autowired
    private IMerInfoDao merInfoDao;

    /**
     * 创建一个空白订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单编号
     */
    public String createBlankOrder(String jsonStr) {
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
        String merchantId = paramJSON.getString("merchantId");
        MerInfo merInfo = merInfoDao.selectMerInfoByMerchantId(merchantId);
        log.info("merchantId:" + merchantId + ";merId" + merInfo.getMerId());
        orderInfo.setMerId(merInfo.getMerId());
        orderInfo.setOrderId(new String(s));
        orderInfo.setCreateTime(date);
        orderInfo.setMerchantId(merchantId);
        orderInfo.setTxnAmt(paramJSON.getLong("txnAmt"));
        orderInfoDao.insertOrder(orderInfo);
        return new String(s);
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

    /**
     * 进行分期交易并更新订单.
     *
     * @param jsonStr 前端访问信息
     * @return 返回创建订单请求报文.
     */
    public Object tradeUpdateOrder(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderId = paramJSON.getString("orderId");
        if (orderId == null || orderId.length() == 0) {
            return false;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setTxnterms(paramJSON.getInteger("txnterms"));
        orderInfo.setAccName(paramJSON.getString("accName"));
        orderInfo.setAccNo(paramJSON.getString("accNo"));
        orderInfo.setAccIdcard(paramJSON.getString("accIdcard"));
        orderInfo.setAccMobile(paramJSON.getString("accMobile"));
        orderInfo.setCvn2(paramJSON.getString("cvn2"));
        orderInfo.setValidity(paramJSON.getString("validity"));
        orderInfo.setOpenId(paramJSON.getString("openId"));
        orderInfoDao.updateOrder(orderInfo);
        return orderInfoDao.selectOrderInfo(orderId);
    }

    /**
     * 查询商户信息.
     *
     * @param orderInfo 包含商户id.
     * @return 商户信息
     */
    public MerInfo findMerInfo(OrderInfo orderInfo) {
        String merchantId = orderInfo.getMerchantId();
        return merInfoDao.selectMerInfoByMerchantId(merchantId);
    }
    /**
     * 更新订单.
     * @param or 订单信息
     * @return true or false
     */
    public boolean updateOrderInfo(OrderInfo or) {
        return orderInfoDao.updateOrder(or);
    }
    /**
     * pos端查询单个订单详细信息.
     * @param orderId 订单号
     * @param merchantId 商户代码
     */
    public OrderInfo findOrderInfoPos(String orderId, String merchantId) {
        if (orderId == null || orderId.length() == 0) {
            return null;
        }
        if (merchantId == null || merchantId.length() == 0) {
            return null;
        }
        return orderInfoDao.selectOrderInfoPos(orderId, merchantId);
    }

    /**
     * 微信顾客查询订单.
     */
    public PageInfo<OrderInfo> getOrderInfoDetailByCustomer(PageModel<OrderInfo> pageModel) {
        String openId = pageModel.getModel().getOpenId();
        String orderId = pageModel.getModel().getOrderId();
        Integer p = pageModel.getPageNum();
        Integer c = pageModel.getPageSize();
        Integer page = null;
        Integer count = null;
        if (p == null || p < 1) {
            page = 1;
        } else {
            page = p;
        }
        if (c == null || c < 5) {
            count = 5;
        } else {
            count = c;
        }
        if (openId == null || "".equals(openId)) {
            return null;
        }
        if ("".equals(orderId)) {
            orderId = null;
        }
        log.info("page=" + page + ";count=" + count + ";openId=" + openId + ";orderId=" + orderId);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("openId", openId);
        map1.put("orderId", orderId);
        //开始分页
        PageHelper.startPage(page, count);
        PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findOrderInfoDetailByCustomer(map1));
        return pageInfo;
    }

    public List<OrderInfo> getOrderInfoListByMerchant(String merchantId, OrderInfo orderInfo) {
        return null;
    }

    public OrderInfo getOrderInfoDetailByMerchant(String merchantId, String orderId) {
        return null;
    }

    public OrderInfo getOrderInfoListByPlantManager(OrderInfo orderInfo) {
        return null;
    }

    public OrderInfo getOrderInfoDetailByPlantManager(String orderId) {
        return null;
    }

}
