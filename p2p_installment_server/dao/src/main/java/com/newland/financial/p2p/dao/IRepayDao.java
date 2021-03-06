package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Repay;

import java.util.List;
import java.util.Map;

/**
 * 还款处理DaoImpl.
 * @author Gregory
 */
public interface IRepayDao {

    /**
     * 更新还款表.
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    boolean updateRepayInfo(Repay repay);

    /**
     * 插入还款表.
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    boolean insertRepayInfo(Repay repay);

    /**
     * 查询换款单.
     * @param repay 还款对象
     * @return 成功：repay，失败：null
     */
    Repay findRepayInfo(Repay repay);

    /**
     * 运营平台商户还款中订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    List<Repay> findRepayList(Map<String, Object> map);
}
