package com.newland.financial.p2p.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 请求报文公共参数.
 *
 * @author Mxia
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class BaseEntityReq implements Serializable {
    /**
     * 重写toString.
     *
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**版本号.*/
    private String version;
    /**编码方式.*/
    private String encoding;
    /**证书ID.*/
    private String certId;
    /**签名.*/
    private String signature;
    /**交易类型.*/
    private String txnType;
    /**发送时间.*/
    private String txnTime;
    /**商户代码.*/
    private String merId;
    /**商户密码.*/
    private String merPwd;
    /**商户名称.*/
    private String merName;
    /**商户简称.*/
    private String merAbbr;
    /**保留域.*/
    private String reserved;
    /**请求方保留域.*/
    private String reqReserved;
}
