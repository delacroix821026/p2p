package com.newland.financial.p2p.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

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

    public PageInfo<MerInfo> getMerchantList(PageModel<MerInfo> pageModel) {
        String merId = pageModel.getModel().getMerId();
        String merName = pageModel.getModel().getMerName();
        Integer p = pageModel.getPageNum();
        Integer c = pageModel.getPageSize();
        Integer page = null;
        Integer count = null;
        if (p == null || p < 1) {
            page = 1;
        } else {
            page = p;
        }
        if (c == null || c < 5) {
            count = 5;
        } else {
            count = c;
        }
        if ("".equals(merId)) {
            merId = null;
        }
        if ("".equals(merName)) {
            merName = null;
        }
        log.info("page=" + page + ";count=" + count + ";merId=" + merId + ";merName=" + merName);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("merId", merId);
        map1.put("merName", merName);
        //开始分页
        PageHelper.startPage(page, count);
        PageInfo<MerInfo> pageInfo = new PageInfo<MerInfo>(iMerInfoDao.findMerchantList(map1));
        return pageInfo;
    }

    public MerInfo getMerchantDetail(String merchantId) {
        return null;
    }

    public void updateMerchantBySystem(MerInfo merInfo) {

    }

    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

    public void updateMerchant(MerInfo merInfo) {

    }


}
