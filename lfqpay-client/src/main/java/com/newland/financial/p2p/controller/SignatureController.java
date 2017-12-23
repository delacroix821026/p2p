package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.ISignatureService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;

/**
 * 验签接口.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/signature")
public class SignatureController {
    /**
     * 验签Service.
     */
    @Autowired
    private ISignatureService signatureService;

    /**
     * 验证签名.
     *
     * @param jsonStr 乐百分请求参数
     * @return 成功：true,失败：false
     */
    @RequestMapping(value = "/sign",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String signature(@RequestBody String jsonStr) throws UnsupportedEncodingException {
        log.info("-------------------------验签:");
        return signatureService.signature(jsonStr);
    }

}
