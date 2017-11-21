package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户信息处理ServiceImpl.
 * @author Gregory
 */
@Log4j
@Service
public class MerchantService implements IMerchantService {
    /**
     * 商户信息处理Dao.
     */
    @Autowired
    private IMerInfoDao iMerInfoDao;

    /**
     * 查询商户信息.
     * @param merId 商户Id
     * @return MerInfo对象
     */
    public CodeMsgReq getMerInfo(String merId) {
        MerInfo merInfo = iMerInfoDao.selectMerInfoByMerId(merId);
        if (merInfo == null) {
            return null;
        }
        CodeMsgReq codeMsgReq = new CodeMsgReq();
        codeMsgReq.setVersion("1.0.0");
        codeMsgReq.setEncoding("utf-8");
        codeMsgReq.setTxnType("13");
        codeMsgReq.setMerId(merInfo.getMerId());
        codeMsgReq.setMerPwd(merInfo.getMerPwd());
        codeMsgReq.setMerName(merInfo.getMerName());
        codeMsgReq.setMerAbbr(merInfo.getMerAbbr());
        return codeMsgReq;
    }
}
