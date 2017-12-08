package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.ILfqMerchantService;
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
    /**
     * service注入.
     */
    @Autowired
    private IMerchantService merchantService;
    /**
     * service注入.
     */
    @Autowired
    private ILfqMerchantService lfqMerchantService;

    /**
     * 管理平台商户列表分页模糊查询.
     *
     * @param jsonStr 查询条件
     * @return List<MerInfo>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getMerchantList(@RequestParam("jsonStr") String jsonStr) {
        log.info("*****client*****");
        ObjectMapper objectMapper = new ObjectMapper();
        PageModel<MerInfo> pageModel = null;
        try {
            pageModel = objectMapper.readValue(jsonStr, new TypeReference<PageModel<MerInfo>>() {
            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        log.info("getMerchantList:" + pageModel.getModel().getMerchantId());
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
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateMerchantBySystem(@RequestBody String jsonStr) {
        log.info("jsonStr" + jsonStr);
        MerInfo merInfo = JSONObject.parseObject(jsonStr, MerInfo.class);
        log.info("MerInfo:" + merInfo.getMerchantId());
        log.info("MerInfo:" + merInfo.getMerName());
        // 先入库
        boolean b = merchantService.updateMerchantBySystem(merInfo);
        log.info("入库结果：" + b);
        Map<String, String> map = new HashMap<String, String>();
        if (b) {
            // 入库成功则请求乐百分
            String resp = lfqMerchantService.updateMerchantBySystem(merInfo);
            log.info("返回结果============:" + resp);
            JSONObject json = JSONObject.parseObject(resp);
            JSONObject paramJSON = JSON.parseObject(resp);
            String code = paramJSON.getString("code");
            if ("success".equals(code)) {
                MerInfo merInfo1 = new MerInfo();
                merInfo1.setScheduleNum(paramJSON.getString("schedule"));
                merInfo1.setMerchantId(merInfo.getMerchantId());
                merchantService.updateMerchantBySystem(merInfo1);
            }
            return json;
        } else {
            // 入库失败则直接返回
            map.put("code", "fail");
            map.put("failure", "更新数据失败，请重新尝试");
            return map;
        }
    }

    /**
     * 商户附件同步（后台附件同步）.
     */
    @RequestMapping(value = "/synchMerchantFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMerchantBySystem(@RequestBody String jsonStr) {

    }

    /**
     * 商户更新（前端输入）.
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody String jsonStr) {
        log.info("merchantId:" + merchantId + ";jsonStr:" + jsonStr);
        MerInfo merInfo = JSONObject.parseObject(jsonStr, MerInfo.class);
        if (merInfo.getMerchantId() == null || "".equals(merInfo.getMerchantId())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("respCode", "0414");
            map.put("respMsg", "操作失败");
            return map;
        }
        return merchantService.updateMerchant(merchantId, merInfo);
    }
}
