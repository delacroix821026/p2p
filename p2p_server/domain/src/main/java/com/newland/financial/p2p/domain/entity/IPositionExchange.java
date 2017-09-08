package com.newland.financial.p2p.domain.entity;

import com.newland.financial.p2p.common.exception.OverloadException;

import java.math.BigDecimal;
/**
 * 定义的Lender类的接口.
 * @author cendaijuan
 * */
public interface IPositionExchange {
    /**
     * 用以更改带钱贷后额度.
     *@param number 贷款金额
     * */
    void changePosition(BigDecimal number);
    /**
     * 获取当前额度.
     *@return 当前额度
     * */
    BigDecimal getPosition();
    /**
     * 获取用户Id.
     *@return 返回用户Id
     * */
    String getUserId();
    /**
     * 判断是否有足够的额度进行贷款.
     *@param position 贷款金额
     * @throws OverloadException 超出限定额度
     * */
    void canDebit(BigDecimal position) throws OverloadException;
}
