package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.CodeMsgReq;
import com.newland.financial.p2p.service.ISignatureService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *验签接口.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/signatureController")
public class SignatureController {

    @Autowired
    private ISignatureService signatureService;

    @RequestMapping(value = "/signature",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String signature(@RequestBody String jsonStr){
        log.info("-------------------------验签:");
        String resp = signatureService.signature(jsonStr);

        return resp;
    }

}
