package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Mxia
 * 费率信息.
 */
@Setter
@Getter
public class Rate extends BaseEntity {
    /**费率编号.*/
    private String id;
    /**商户代码.*/
    private String merId;
    /**分期数.*/
    private Integer txnTerms;
    /**费率.*/
    private BigDecimal rate;
}
