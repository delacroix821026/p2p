package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Delacroix
 */
@RestController
@Log4j
@RequestMapping("/merchant")
public class MerchantController {
    /**service注入.*/
    @Autowired
    private IMerchantService merchantService;
    /**
     * 管理平台商户列表分页模糊查询.
     *@param jsonStr 查询条件
     * @return List<MerInfo>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getMerchantList(@RequestParam("jsonStr") String jsonStr) {
        log.info("*****client*****");
        ObjectMapper objectMapper = new ObjectMapper();
        PageModel<MerInfo> pageModel = null;
        try {
            pageModel = objectMapper.readValue(jsonStr, new TypeReference<PageModel<MerInfo>>() {});
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
    @RequestMapping(value = "/{merId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MerInfo getMerchantDetail(@PathVariable(name = "merId") String merId) {
        return null;
    }

    /**
     * 商户同步（后台同步）.
     *
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMerchantBySystem(@RequestBody String jsonStr) {
        log.info("jsonStr" + jsonStr);
        MerInfo merInfo = JSONObject.parseObject(jsonStr, MerInfo.class);
        log.info("MerInfo:" + merInfo.getMerId());
        log.info("MerInfo:" + merInfo.getMerName());
        log.info("MerInfo" + merInfo.getRateSix());

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
    @RequestMapping(value = "/{merId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateMerchant(@PathVariable(name = "merId") String merId, @RequestBody String jsonStr) {
        log.info("merId:" + merId + ";jsonStr:" + jsonStr);
        MerInfo merInfo = JSONObject.parseObject(jsonStr, MerInfo.class);
        if (merInfo.getMerId() == null || "".equals(merInfo.getMerId())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("respCode", "0414");
            map.put("respMsg", "操作失败");
            return map;
        }
        return merchantService.updateMerchant(merId, merInfo);
    }

}
