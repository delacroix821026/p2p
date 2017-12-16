package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 商户信息处理Controller.
 *
 * @author Gregory
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
     * 管理平台商户列表查询，支持模糊查询.
     *
     * @param pageModel 包含查询条件
     * @return 分页结果
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object getMerchantList(@RequestBody PageModel<MerInfo> pageModel) {
        log.info("*********---------***********");
        log.info("getMerchantList:" + pageModel.getModel().getMerchantId());
        log.info("getMerchantList:" + pageModel.getModel().getMerName());
        log.info("getMerchantList:" + pageModel.getPageSize());
        log.info("getMerchantList:" + pageModel.getPageNum());
        return merchantService.getMerchantList(pageModel);
    }

    /**
     * 商户详情查询.
     *
     * @param merchantId 商户ID
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
     * @param merInfo
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateMerchantBySystem(@RequestBody MerInfo merInfo) {
        log.info(merInfo.toString());
        return merchantService.updateMerchantBySystem(merInfo);
    }

    /**
     * 商户附件同步（后台附件同步）.
     *
     * @param merInfo
     */
    @RequestMapping(value = "/synchMerchantFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

    /**
     * 商户更新（前端输入）.
     *
     * @param merInfo
     * @param merchantId
     * @return Object
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object updateMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody MerInfo merInfo) {
        MerInfo mer = merInfo;
        log.info(mer.toString());
        if ("".equals(mer.getContractsCode())) {
            mer.setContractsCode(null);
        }
        boolean b = merchantService.updateMerchant(mer);
        Map<String, String> map = new HashMap<String, String>();
        if (b) {
            map.put("respCode", "0000");
            map.put("respMsg", "操作成功");
        } else {
            map.put("respCode", "0414");
            map.put("respMsg", "操作失败");
        }
        return map;
    }
}
