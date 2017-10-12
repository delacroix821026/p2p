package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 反选机构实体类.
 */
@Setter
@Getter
public class OrgNegative {
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
    /**机构状态.*/
    private String orgStus;
}
