package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * 产品对应扣款方式实体.
 * @author Mxia
 */
@Setter
@Getter
public class CutMethod extends BaseEntity {
    /**自增长主键.*/
    private int id;
    /**产品编号.*/
    private String proId;
    /**产品对应扣款方式：1银行代扣，2自主还款.*/
    private String cutMhd;
}
