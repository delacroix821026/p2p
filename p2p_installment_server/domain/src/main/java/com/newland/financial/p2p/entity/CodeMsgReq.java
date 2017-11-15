package com.newland.financial.p2p.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *@author Mxia
 * 短信请求报文信息.
 */
@Setter
@Getter
public class CodeMsgReq extends BaseEntityReq {
    /**手机号.*/
    private String mobile;
}
