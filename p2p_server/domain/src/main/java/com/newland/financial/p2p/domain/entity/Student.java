package com.newland.financial.p2p.domain.entity;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * test.
 * */
@Setter
@Getter
public class Student extends BaseEntity {
    /***/
    private String id = "id111";
    /***/
    private String name = "name222";
    /***/
    private BigDecimal avgScore = new BigDecimal(99);
    /***/
    private Date createTime = new Date();

}
