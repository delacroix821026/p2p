package com.newland.financial.p2p.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mxia
 * 商户信息实体类.
 */
@Setter
@Getter
public class MerInfo extends BaseEntity {
    /**主键Id.*/
    private String id;
    /**商户代码.*/
    private String merId;
    /**商户名称.*/
    private String merName;
    /**商户密码.*/
    private String merPwd;
    /**商户简称.*/
    private String merAbbr;
    /**注册地址.*/
    private String registeredAddress;
    /**上级渠道商编码.*/
    private String parentChannelsCode;
    /**上级商户编码.*/
    private String parentMerchantCode;
    /**营业地址.*/
    private String addr;
    /**法人代表.*/
    private String corporateRepresentative;
    /**联系人.*/
    private String lxr;
    /**办公电话.*/
    private String tel;
    /**移动电话.*/
    private String mobile;
    /**电子邮箱.*/
    private String email;
    /**传真号码.*/
    private String fax;
    /**营业用地性质(1：自用；2：租用).*/
    private String nature;
    /**租赁到期时间,自用可以为空，格式：yyyy-MM-dd.*/
    private String lease;
    /**营业用地面积,单位：平方米（请四舍五入保留整数）.*/
    private Integer sitearea;
    /**开业时间,格式：yyyy-MM-dd.*/
    private String startBusiness;
    /**营业时间,例：10小时.*/
    private Integer businessHourse;
    /**经营产品.*/
    private String industriesId;
    /**经营产品说明.*/
    private String industriesExplai;
    /**预计月平均银行卡营业额,单位：元（请四舍五入保留整数）.*/
    private Integer bankcardTurnover;
    /**预计每张签购单平均交易额,单位：元（请四舍五入保留整数）.*/
    private Integer salesslipTurnover;
    /**预计月平均乐百分营业额,单位：元（请四舍五入保留整数）.*/
    private Integer lfqTurnover;
    /**渠道商编号.*/
    private String channelsId;
    /**商户账户开户行名称.*/
    private String bankName;
    /**商户账户开户行号.*/
    private String bankNum;
    /**商户账户户名.*/
    private String holderName;
    /**商户账户银行账号.*/
    private String cardNum;
    /**银联商户号.*/
    private String unionpayMerchantNum;
    /**母公司名称.*/
    private String parentName;
    /**证件类型,1：新营业执照；2：旧的营业执照.*/
    private String level;
    /**备注.*/
    private String remark;
    /**商户状态，0:正常  1:冻结.*/
    private String state;
    /**接入审批状态，0-入网成功，1-审核中，2-审核失败.*/
    private String scheduleState;
    /**6期费率.*/
    private BigDecimal rateSix;
    /**12期费率.*/
    private BigDecimal rateTwelve;
    /**24期费率.*/
    private BigDecimal rateTwentyFour;
    /**补贴方式：0商户补贴，1消费者补贴，2按比例补贴.*/
    private String subsidy;
    /**接入时间.*/
    private Date createTime;
    /**合同号.*/
    private String contractsCode;
    /**接入失败原因.*/
    private String failure;
    /**新大陆处商户Id.*/
    private String merchantId;
    /**商户查询码.*/
    private String scheduleNum;
    /**手续费补贴商户占比（只在按比例补贴模式有值）.*/
    private BigDecimal merchantProportion;
    /**手续费补贴消费者占比（只在按比例补贴模式有值）.*/
    private BigDecimal customerProportion;
}
