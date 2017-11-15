package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.CodeMsgReq;
import com.newland.financial.p2p.domain.CodeMsgResp;

/**
 *短信验证码Service.
 * @author Gregory
 */
public interface ISmsCodeServie {

    /**
     * 请求乐百通短信接口.
     * @param codeMsgReq
     * @return
     */
    CodeMsgResp backSMSCodeRequest(CodeMsgReq codeMsgReq);
}
