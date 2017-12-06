package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Delacroix
 */
@RestController
@Log4j
@RequestMapping("/merchant")
public class MerchantController {
    /**service注用.*/
    @Autowired
    private IMerchantService merchantService;
    /**
     * 商户查询.
     *
     * @return List<MerInfo>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getMerchantList(@RequestParam("jsonStr") String jsonStr) {
        log.info("*****client*****");
        PageModel<MerInfo> pageModel = JSONObject.parseObject(jsonStr, new TypeReference<PageModel<MerInfo>>() {});
        //PageModel<MerInfo> pageModel = JSONObject.parseObject(jsonStr, PageModel.class);
        MerInfo merInfo = JSONObject.parseObject(jsonStr, MerInfo.class);
        pageModel.setModel(merInfo);
        log.info("getMerchantList:" + pageModel.getModel().getMerId());
        log.info("getMerchantList:" + pageModel.getModel().getMerName());
        log.info("getMerchantList:" + pageModel.getPageNum());
        log.info("getMerchantList:" + pageModel.getPageSize());
        return merchantService.getMerchantList(pageModel);
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
