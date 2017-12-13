package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.domain.entity.Refund;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 退款处理DaoImpl.
 *
 * @author Gregory
 */
@Repository
@Log4j
public class RefundDaoImpl extends MybatisBaseDao<Refund> implements IRefundDao {
    /**
     * 运营平台商户退款中
     * @param map 查询条件
     * @return 信息集合
     */
    public List<Refund> findRefundList(Map<String, Object> map) {
        return super.select("findFundInfo", map);
    }
    /**
     * 商户退款订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<Refund> getOrderInfoListByMerchant(Map<String, Object> map) {
        return super.select("getOrderInfoListByMerchant", map);
    }
    /**
     * 插入或者更新Refund.
     * @param refund 退款单
     */
    public Object insertOrUpdateRefund(Refund refund) {
        String orderId = refund.getOrderId();
        log.info("Dao层orderId:" + orderId);
        // 查找是否存在
        Refund re = super.selectEntity("selectRefundByOrderId", orderId);
        if (re != null) {
            log.info("==================进行更新=================");
            // 存在则更新
           return super.update("updateRefundByOrderId", refund);
        } else {
            log.info("==================进行插入=================");
            // 不存在则插入
            return super.insert("insertRefund", refund);
        }
    }
}
