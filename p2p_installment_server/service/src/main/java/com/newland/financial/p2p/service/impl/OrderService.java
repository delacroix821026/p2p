package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单处理ServiceImpl.
 *
 * @author Gregory, Mxia
 */
@Log4j
@Service
public class OrderService implements IOrderService {
    /**
     * 注入Dao层对象.
     */
    @Autowired
    private IOrderInfoDao orderInfoDao;
    /**
     * 注入Dao层对象.
     */
    @Autowired
    private IMerInfoDao merInfoDao;

    /**
     * 创建一个空白订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单编号.
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
        orderInfo.setApplyCancelAmount(paramJSON.getLong("txnAmt")); //申请退款金额，暂时和订单金额相等，后期逻辑可能修改
        orderInfoDao.insertOrder(orderInfo);
        return new String(s);
    }

    /**
     * 获取相应订单信息.
     *
     * @param orderId 订单编号.
     * @return 订单信息.
     */
    public OrderInfo findOrderInfo(String orderId) {
        //判断orderId是否为空
        if (orderId == null || orderId.length() == 0) {
            log.info("===Exception:2451===");
            throw new BaseRuntimeException("2451");
        }
        return orderInfoDao.selectOrderInfo(orderId);
    }

    /**
     * 进行分期交易并更新订单.
     *
     * @param jsonStr 前端访问信息.
     * @return 返回创建订单请求报文.
     */
    public Object tradeUpdateOrder(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderId = paramJSON.getString("orderId");
        if (orderId == null || orderId.length() == 0) {
            log.info("===Exception:2451===");
            throw new BaseRuntimeException("2451");
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
     * @return 商户信息.
     */
    public MerInfo findMerInfo(OrderInfo orderInfo) {
        String merchantId = orderInfo.getMerchantId();
        return merInfoDao.selectMerInfoByMerchantId(merchantId);
    }

    /**
     * 更新订单.
     *
     * @param or 订单信息.
     * @return true or false.
     */
    public boolean updateOrderInfo(OrderInfo or) {
        return orderInfoDao.updateOrder(or);
    }

    /**
     * pos端查询单个订单详细信息.
     *
     * @param orderId    订单号.
     * @param merchantId 商户代码.
     * @return 订单信息.
     */
    public OrderInfo findOrderInfoPos(String orderId, String merchantId) {
        if (orderId == null || orderId.length() == 0) {
            log.info("===Exception:2451===");
            throw new BaseRuntimeException("2451");
        }
        if (merchantId == null || merchantId.length() == 0) {
            log.info("===Exception:2452===");
            throw new BaseRuntimeException("2452");
        }
        return orderInfoDao.selectOrderInfoPos(orderId, merchantId);
    }

    /**
     * 微信顾客查询订单.
     *
     * @param jsonStr   数据.
     * @return 订单信息.
     */
    public PageInfo<OrderInfo> getOrderInfoListByCustomer(String jsonStr) {
        log.info("======getOrderInfoListByCustomer======");
        log.info("jsonStr" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String openId = paramJSON.getString("openId");
        String orderId = paramJSON.getString("orderId");
        String accName = paramJSON.getString("accName");
        String status = paramJSON.getString("status"); //0：全部，1：还款中，2，退款中，3，已结清
        Integer c = paramJSON.getInteger("pageSize");
        Integer p = paramJSON.getInteger("pageNum");
        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long createTimeBeg = paramJSON.getLong("beginTime");
        Long createTimeEnd = paramJSON.getLong("endTime");
        //时间转化
        String begTime = null;
        String endTime = null;
        if (createTimeBeg != null) {
            Date begTimeDate = new Date(createTimeBeg);
            begTime = sdf.format(begTimeDate);
            log.debug("---------------------------------" + begTime);
        }
        if (createTimeEnd != null) {
            Date endTimeDate = new Date(createTimeEnd);
            endTime = sdf.format(endTimeDate);
            log.debug("---------------------------------" + endTime);
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
        if ("".equals(status)) {
            //0：全部，1：未结清，2，已结清，3退款
            status = null;
        }
        log.info("page=" + page + ";count=" + count + ";openId=" + openId + ";orderId=" + orderId + ";accName=" + accName
                + ";status=" + status + ";begTime=" + begTime + ";endTime=" + endTime);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("openId", openId);
        map1.put("orderId", orderId);
        map1.put("accName", accName);
        map1.put("status", status);
        map1.put("begTime", begTime);
        map1.put("endTime", endTime);
        //开始分页
        PageHelper.startPage(page, count);
        if ("1".equals(status)) {
            log.info("========还款中=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findRepayWeixin(map1));
            return pageInfo;
        } else if ("2".equals(status)) {
            log.info("========退款中=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findRefundWeixin(map1));
            return pageInfo;
        } else {
            if ("3".equals(status)) {
                log.info("========已结清=======");
                status = null;
                map1.put("status", status);
            }
            log.info("========全部=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findByFinishWeixin(map1));
            return pageInfo;
        }
    }

    /**
     * Pos端订单查询(列表).
     *
     * @param jsonStr   数据.
     * @param merchantId 商户代码.
     * @return 订单信息.
     */
    public PageInfo getOrderInfoListByMerchant(String merchantId, String jsonStr) {
        log.info("jsonStr" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String orderId = paramJSON.getString("orderId");
        String accName = paramJSON.getString("accName");
        String status = paramJSON.getString("status"); //0：全部，1：未结清，2，已结清，3退款
        Integer c = paramJSON.getInteger("pageSize");
        Integer p = paramJSON.getInteger("pageNum");
        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long createTimeBeg = paramJSON.getLong("beginTime");
        Long createTimeEnd = paramJSON.getLong("endTime");
        //时间转化
        String begTime = null;
        String endTime = null;
        if (createTimeBeg != null) {
            Date begTimeDate = new Date(createTimeBeg);
            begTime = sdf.format(begTimeDate);
            log.debug("---------------------------------" + begTime);
        }
        if (createTimeEnd != null) {
            Date endTimeDate = new Date(createTimeEnd);
            endTime = sdf.format(endTimeDate);
            log.debug("---------------------------------" + endTime);
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
        if ("".equals(status)) {
            //0：全部，1：未结清，2，已结清，3退款
            status = null;
        }
        log.info("page=" + page + ";count=" + count + ";merchantId=" + merchantId + ";orderId=" + orderId + ";accName=" + accName
                + ";status=" + status + ";begTime=" + begTime + ";endTime=" + endTime);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("merchantId", merchantId);
        map1.put("orderId", orderId);
        map1.put("accName", accName);
        map1.put("status", status);
        map1.put("begTime", begTime);
        map1.put("endTime", endTime);
        //开始分页
        PageHelper.startPage(page, count);
        if ("0".equals(status)) {
            log.info("========查询全部=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.getOrderInfoListByMerchant(map1));
            return pageInfo;
        } else if ("3".equals(status)) {
            log.info("========退款中=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findRefundListPos(map1));
            return pageInfo;
        } else {
            if ("1".equals(status)) {
                status = null;
                log.info("========未结清=======");
                map1.put("status", status);
            }
            log.info("========已结清=======");
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findOrderListPos(map1));
            return pageInfo;
        }
    }

    /**
     * 运营平台商户查询.
     *
     * @param pageModel   数据.
     * @return 订单信息.
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
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findRepayList(map1));
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
            PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.findRefundList(map1));
            return pageInfo;
        }
        return null;
    }

    /**
     * 微信端查询订单信息.
     *
     * @param openId  微信Id.
     * @param orderId 订单id.
     * @return orderInfo.
     */
    public OrderInfo findOrderInfoWeiXin(String openId, String orderId) {
        return orderInfoDao.findOrderInfoWeiXin(openId, orderId);
    }
    /**
     * 商户查询订单信息.
     *
     * @param orderId 订单id.
     * @return orderInfo.
     */
    public OrderInfo findOrderInfoManager(String orderId) {
        return orderInfoDao.selectOrderInfo(orderId);
    }
    /**
     * 商户查询订单详情.
     *
     * @param orderId 订单id.
     * @return orderInfo.
     */
    public OrderInfo getOrderInfoByManager(String orderId) {
        return orderInfoDao.getOrderInfoByManager(orderId);
    }
    /**
     * 平台管理员查询退款订单.
     *
     * @param jsonStr   数据.
     * @return 订单信息.
     */
    public PageInfo<OrderInfo> getOrderRundListByPlantManager(String jsonStr) {
        log.info("======getOrderRundListByPlantManager======");
        log.info("jsonStr" + jsonStr);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray result = jsonObj.getJSONArray("merchantId");
        List<String> list = JSON.parseArray(result.toJSONString(),String.class);

        Integer c = jsonObj.getInteger("pageSize");
        Integer p = jsonObj.getInteger("pageNum");
        
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
        log.info("page=" + page + ";count=" + count + ";List<OrderInfo>=" + list);

        //开始分页
        PageHelper.startPage(page, count);
        PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoDao.getOrderRundListByPlantManager(list));
        return pageInfo;
    }
}
