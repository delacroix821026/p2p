package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.entity.MerInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Delacroix
 */
@RestController
@Log4j
@RequestMapping("/merchant")
public class MerchantController {
    /**
     * 商户查询.
     *
     * @return List<MerInfo>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MerInfo> getMerchantList(@RequestBody String jsonStr) {
        return null;
    }

    /**
     * 商户详情查询.
     *
     * @return MerInfo
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MerInfo getMerchantDetail(@PathVariable(name = "merchantId") String merchantId) {
        return null;
    }

    /**
     * 商户同步（后台同步）.
     *
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMerchantBySystem(@RequestBody String jsonStr) {

    }

    /**
     * 商户附件同步（后台附件同步）.
     *
     */
    @RequestMapping(value = "/synchMerchantFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMerchantBySystem(@RequestBody String jsonStr) {

    }

    /**
     * 商户更新（前端输入）.
     *
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody String jsonStr) {

    }

}
