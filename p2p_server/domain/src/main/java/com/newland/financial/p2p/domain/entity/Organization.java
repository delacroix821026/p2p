package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * 产品机构权限.
 *@author Mxia
 */
@Setter
@Getter
public class Organization {
    /**主键.*/
    private int id;
    /**机构号.*/
    private String organization;
    /**产品编号.*/
    private String productId;
}
