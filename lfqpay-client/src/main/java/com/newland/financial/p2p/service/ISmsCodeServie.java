package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.CodeMsgReq;

/**
 * 短信验证码Service.
 *
 * @author Gregory
 */
public interface ISmsCodeServie {

    /**
     * 请求乐百通短信接口.
     *
     * @param codeMsgReq 信接口请求参数
     * @return  乐百分响应报文
     */
    Object backSMSCodeRequest(CodeMsgReq codeMsgReq);
}
