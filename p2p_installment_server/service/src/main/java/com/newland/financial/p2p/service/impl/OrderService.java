package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.Repay;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
     * 注入Dao层对象.
     */
    @Autowired
    private IRefundDao refundDao;
    /**
     * 注入Dao层对象.
     */
    @Autowired
    private IRepayDao repayDao;
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
        orderInfo.setMerName(merInfo.getMerName());
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
    /**
     * Pos端订单查询(列表)
     */
    public PageInfo getOrderInfoListByMerchant(String merchantId, String jsonStr) {
        log.info("jsonStr" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderId =paramJSON.getString("orderId");
        String accName =paramJSON.getString("accName");
        String status =paramJSON.getString("status"); //0：全部，1：未结清，2，已结清，3退款
        Integer c = paramJSON.getInteger("pageSize");
        Integer p = paramJSON.getInteger("pageNum");
        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long beginTime =paramJSON.getLong("beginTime");
        Long endTime =paramJSON.getLong("endTime");
        //时间转化
        String startTime = null;
        String endsTime = null;
        if (beginTime != null) {
            Date beginTimeDate = new Date(beginTime);
            startTime = sdf.format(beginTimeDate);
            log.info("---------------------------------" + startTime);
        }
        if (endTime != null) {
            Date endTimeDate = new Date(endTime);
            endsTime = sdf.format(endTimeDate);
            log.info("---------------------------------" + endsTime);
        }

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
        if ("".equals(orderId)) {
            orderId = null;
        }
        if ("".equals(accName)) {
            accName = null;
        }
        if ("".equals(status)){
            //0：全部，1：未结清，2，已结清，3退款
            status = null;
        }
        log.info("page=" + page + ";count=" + count + ";merchantId=" + merchantId + ";orderId=" + orderId + ";accName=" + accName
                + ";status=" + status+ ";startTime=" + startTime+ ";endsTime=" + endsTime);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("merchantId", merchantId);
        map1.put("orderId", orderId);
        map1.put("accName", accName);
        map1.put("status", status);
        map1.put("startTime", startTime);
        map1.put("endsTime", endsTime);
        //开始分页
        PageHelper.startPage(page, count);
        if ("3".equals(status)){
            log.info("========退款=======");
            PageInfo<Refund> pageInfo = new PageInfo<Refund>(refundDao.getOrderInfoListByMerchant(map1)) ;
            return pageInfo;
        }else if("0".equals(status)){
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.getOrderInfoListByMerchant(map1));
            return pageInfo;
        }
        return null;
    }

    public OrderInfo getOrderInfoDetailByMerchant(String merchantId, String orderId) {
        return null;
    }

    /**
     * 运营平台商户查询
     */
    public PageInfo getOrderInfoListByPlantManager(PageModel<OrderInfo> pageModel) {
        String merchantId = pageModel.getModel().getMerchantId();
        String orderId = pageModel.getModel().getOrderId();
        String merName = pageModel.getModel().getMerName();
        String contractsState = pageModel.getModel().getContractsState();
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
        if ("".equals(merchantId)) {
            merchantId = null;
        }
        if ("".equals(merName)) {
            merName = null;
        }
        if ("".equals(orderId)) {
            orderId = null;
        }
        if ("".equals(contractsState)) {
            contractsState = null;
        }
        log.info("page=" + page + ";count=" + count + ";merchantId=" + merchantId + ";orderId=" + orderId + ";merName=" + merName + ";contractsState=" + contractsState);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("merchantId", merchantId);
        map1.put("orderId", orderId);
        map1.put("merName", merName);
        map1.put("contractsState", contractsState);
        //开始分页
        PageHelper.startPage(page, count);
        if (contractsState == null) {
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findOrderInfoListByPlantManager(map1));
            return pageInfo;
        }
        // 0 还款中
        if ("0".equals(contractsState)) {
            log.info("========0000===========");
            PageInfo<Repay> pageInfo = new PageInfo<Repay>(repayDao.findRepayList(map1)) ;
            return pageInfo;
        }
        // 1 结清
        if ("1".equals(contractsState)) {
            log.info("========11111===========");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findOrderInfoListByFinish(map1));
            return pageInfo;
        }
        // 2 退款中
        if ("2".equals(contractsState)) {
            log.info("========222222===========");
            PageInfo<Refund> pageInfo = new PageInfo<Refund>(refundDao.findRefundList(map1)) ;
            return pageInfo;
        }
        return null;
    }

    public OrderInfo getOrderInfoDetailByPlantManager(String orderId) {
        return null;
    }

}
