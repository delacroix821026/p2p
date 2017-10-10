package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 产品对应还款方式实体.
 * @author Mxia
 */
@Setter
@Getter
public class RepayMethod {
    /**自增长主键.*/
    private int id;
    /**产品编号.*/
    private String proId;
    /**还款方式：1等额本息，2等额本金.*/
    private String repayMhd;
}
