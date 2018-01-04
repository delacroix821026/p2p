package com.newland.financial.p2p.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.exception.BaseException;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 商户信息处理ServiceImpl.
 *
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
     *
     * @param merchantId 商户Id
     * @return MerInfo对象
     */
    public CodeMsgReq getMerInfo(String merchantId) {
        MerInfo merInfo = iMerInfoDao.selectMerInfoByMerchantId(merchantId);
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

    /**
     * 管理平台获取商户列表.
     *
     * @param pageModel 查询条件
     * @return 分页结果
     */
    public PageInfo<MerInfo> getMerchantList(PageModel<MerInfo> pageModel) {
        String merchantId = pageModel.getModel().getMerchantId();
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
        if ("".equals(merchantId)) {
            merchantId = null;
        }
        if ("".equals(merName)) {
            merName = null;
        }
        log.info("page=" + page + ";count=" + count + ";merId=" + merchantId + ";merName=" + merName);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("merchantId", merchantId);
        map1.put("merName", merName);
        //开始分页
        PageHelper.startPage(page, count);
        PageInfo<MerInfo> pageInfo = new PageInfo<MerInfo>(iMerInfoDao.findMerchantList(map1));
        return pageInfo;
    }

    /**
     * 根据merchantId来获取商户信息.
     *
     * @param merchantId 新大陆商户id
     * @return 相关信息
     */
    public MerInfo getMerchantDetail(String merchantId) {
        return iMerInfoDao.selectMerInfoByMerchantId(merchantId);
    }

    /**
     * 商户接入,有则更新，无则插入.
     *
     * @param merInfo
     * @return 最新的商户信息
     */
    public boolean updateMerchantBySystem(MerInfo merInfo) {
        // 查找是否已经拥有记录，有就更新，无则插入.
        String merchantId = merInfo.getMerchantId();
        MerInfo mer = iMerInfoDao.selectMerInfoByMerchantId(merchantId);
        log.info("是否已存在改商户号" + (mer == null));
        if (mer == null) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            merInfo.setId(id);
            Date date = new Date();
            merInfo.setCreateTime(date);
            return iMerInfoDao.insertMerInfo(merInfo);
        } else {
            return iMerInfoDao.updateMerInfo(merInfo);
        }
    }

    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

    /**
     * 更新商户信息(费率和合同号).
     *
     * @param merInfo 更新内容
     * @return boolean
     */
    public boolean updateMerchant(MerInfo merInfo) {
        // 判断按比例分配相加是否等于100
        if (merInfo.getMerchantProportion() != null || merInfo.getCustomerProportion() != null || "2".equals(merInfo.getSubsidy())) {
            BigDecimal mpo = merInfo.getMerchantProportion();
            BigDecimal cpo = merInfo.getCustomerProportion();
            if (mpo == null || cpo == null) {
                log.info("===Exception:2412===");
                throw new BaseRuntimeException("2412");
            }
            if (mpo.add(cpo).doubleValue() != 100) {
                log.info("===Exception:2412===");
                throw new BaseRuntimeException("2412");
            }
        }
        // 判断是否传入的都是空值
        if (merInfo.getRateSix() == null && merInfo.getRateTwelve() == null && merInfo.getRateTwentyFour() == null
                && (merInfo.getContractsCode() == null || "".equals(merInfo.getContractsCode().trim())) &&
                (merInfo.getSubsidy() == null || "".equals(merInfo.getSubsidy().trim())) &&
                merInfo.getMerchantProportion() == null && merInfo.getCustomerProportion() == null) {
            log.info("===Exception:2413===");
            throw new BaseRuntimeException("2413");
        }
        return iMerInfoDao.updateMerchant(merInfo);
    }

}
