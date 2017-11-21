package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Repay;

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
}
