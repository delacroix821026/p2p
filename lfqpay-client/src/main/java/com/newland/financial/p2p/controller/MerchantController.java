package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户信息处理Controller.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/merchant")
public class MerchantController {
    /**注入service对象.*/
    @Autowired
    private IMerchantService merchantService;
    /**
     * 商户同步（后台同步）.
     *
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String updateMerchantBySystem(@RequestBody MerInfo merInfo) {
       return merchantService.updateMerchantBySystem(merInfo);
    }

    /**
     * 商户附件同步（后台附件同步）.
     *
     */
    @RequestMapping(value = "/synchMerchantFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

}
