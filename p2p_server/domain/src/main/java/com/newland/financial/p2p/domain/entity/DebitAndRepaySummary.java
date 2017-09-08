package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * 定义未还,已还,本月应还的实体类.
 * @author cendaijuan
 * */
@Setter
@Getter
public class DebitAndRepaySummary {
    /**
     * 未还.
     * */
    private BigDecimal needRepay;
    /**
     * 已还.
     * */
    private BigDecimal repayed;
    /**
     *本月应还.
     * */
    private BigDecimal repayInMonth;

}
