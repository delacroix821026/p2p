package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 短信验证码应答报文实体.
 */
@Getter
@Setter
public class CodeMsgResp extends BaseEntityResp {
    /**手机号.*/
    private String mobile;
}
