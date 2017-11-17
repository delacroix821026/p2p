package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.CodeMsgReq;
import com.newland.financial.p2p.domain.CodeMsgResp;

/**
 *验签接口.
 * @author Gregory
 */
public interface ISignatureService {

    /**
     * 验签.
     * @param jsonStr 验签字段
     * @return  成功：true,失败：false
     */
    String signature(String jsonStr);
}
