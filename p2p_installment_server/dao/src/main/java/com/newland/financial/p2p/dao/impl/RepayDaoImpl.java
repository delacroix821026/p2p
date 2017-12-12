package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.Repay;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 还款处理DaoImpl.
 * @author Gregory
 */
@Repository
public class RepayDaoImpl extends MybatisBaseDao<Repay> implements IRepayDao {

    /**
     * 更新还款表.
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    public boolean updateRepayInfo(Repay repay) {
        return super.update("updateRepayInfo", repay);
    }

    /**
     * 插入还款表.
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    public boolean insertRepayInfo(Repay repay) {
        return super.insert("insertRepayInfo", repay);
    }

    /**
     * 查询换款单.
     * @param repay 还款对象
     * @return 成功：repay，失败：null
     */
    public Repay findRepayInfo(Repay repay) {
        return super.selectEntity("findRepayInfo", repay);
    }
    /**
     * 运营平台商户还款中订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<Repay> findRepayList(Map<String, Object> map) {
        return super.select("findRepayList", map);
    }
}
