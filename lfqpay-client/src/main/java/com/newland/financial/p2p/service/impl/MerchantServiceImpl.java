package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.domain.MethodFactory;
import com.newland.financial.p2p.domain.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author Mxia
 */
@Log4j
@Service
public class MerchantServiceImpl implements IMerchantService {
    /**
     * 还款推送URL.
     */
    @Value("${IFQ_MERCHANT_ADDRESS}")
    private String merchantUrl;
    /**
     * 渠道商编码.
     */
    @Value("${parentChannelsCode}")
    private String parentChannelsCode;
    /**
     * 渠道商密码.
     */
    @Value("${parentChannelsPwd}")
    private String parentChannelsPwd;

    /**
     * 商户接入.
     *
     * @param merInfo 商户信息
     */
    public String updateMerchantBySystem(MerInfo merInfo) {
        log.info("merchantUrl:" + merchantUrl + ";parentMerchantCode:" + parentChannelsCode + ";parentMerchantPwd:" + parentChannelsPwd);
        Map<String, String> data = MethodFactory.installMerchantInfo(merInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        data.put("parentChannelsCode", parentChannelsCode); //渠道商编码
        data.put("time", time); //发送时间
        data.put("sign", DigestUtils.md5Hex(parentChannelsPwd + time)); //渠道商密码加签
        return MethodFactory.execute(merchantUrl, data);
    }

    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

}
