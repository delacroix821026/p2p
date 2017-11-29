package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单报表信息实体类.
 *
 * @author Gregory
 */
@Getter
@Setter
public class ExcelOrderModel extends BaseEntity {
    /**
     * 申请时间.
     */
    private Date dDate;
    /**
     * 申请单号.
     */
    private String oddNumbers;
    /**
     * 申请人姓名.
     */
    private String applyName;
    /**
     * 电话.
     */
    private String phone;
    /**
     * 申请金额.
     */
    private BigDecimal dMoney;
    /**
     * 省份.
     */
    private String province;
    /**
     * 市.
     */
    private String city;
    /**
     * 区或县.
     */
    private String region;
    /**
     * 详细地址.
     */
    private String detailAdd;
    /**
     * 身份证号.
     */
    private String identityCard;
    /**
     * 法人手机号.
     */
//    private String corpPhone;
    /**
     * 所拥有商户.
     */
//    private String merchantName;
    /**
     * 绑定的商户号.
     */
    private String merchantNum;
    /**
     * 是否已发送.
     */
    private String isSend;
    /**
     * 发送时间.
     */
    private Date transformTime;

    @Override
    public String toString() {
        return this.getDDate() + "," + this.getOddNumbers() + "," + this.getApplyName() + ","
                + this.getPhone() + "," + this.getDMoney() + "," + this.getProvince() + ","
                + this.getCity() + "," + this.getRegion() + "," +this.getDetailAdd() + ","
                + this.getIdentityCard() + "," + this.getMerchantNum();
    }
}
