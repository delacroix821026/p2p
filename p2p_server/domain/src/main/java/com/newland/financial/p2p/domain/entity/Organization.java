package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * 产品机构权限.
 *@author Mxia
 */
@Setter
@Getter
public class Organization extends BaseEntity {
    /**主键.*/
    private int id;
    /**产品编号.*/
    private String proId;
    /**机构号.*/
    private String organization;
    /**机构名称.*/
    private String orgaName;
    /**机构的父级机构号.*/
    private String parentId;
    /**1正选，2反选.*/
    private String positiveOrNegative;
}
