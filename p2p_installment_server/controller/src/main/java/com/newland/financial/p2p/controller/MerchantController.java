package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商户信息处理Controller.
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/MerchantController")
public class MerchantController {

    /**
     * 生成短信接口请求报文.
     * @param jsonStr 请求参数：<BR>
     *                {<BR>
     *                &nbsp;"merId":"商户代码"<BR>
     *                }
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;"merId":"商户代码",<BR>
     * &nbsp;"merName":"商户名称",<BR>
     * &nbsp;"merPwd":"商户密码",<BR>
     * &nbsp;"merAbbr":"商户简称",<BR>
     * &nbsp;"registeredAddress":"注册地址",<BR>
     * &nbsp;"parentChannelsCode":"上级渠道商编码",<BR>
     * &nbsp;"addr":"营业地址",<BR>
     * &nbsp;"corporateRepresentative":"法人代表",<BR>
     * &nbsp;"lxr":"联系人",<BR>
     * &nbsp;"tel":"办公电话",<BR>
     * &nbsp;"email":"电子邮箱",<BR>
     * &nbsp;"fax":"传真号码",<BR>
     * &nbsp;"nature":"营业用地性质(1：自用；2：租用)",<BR>
     * &nbsp;"lease":"租赁到期时间,自用可以为空，格式：yyyy-MM-dd",<BR>
     * &nbsp;"sitearea":"营业用地面积,单位：平方米（请四舍五入保留整数）",<BR>
     * &nbsp;"startBusiness":"开业时间,格式：yyyy-MM-dd",<BR>
     * &nbsp;"businessHourse":"营业时间,例：10小时",<BR>
     * &nbsp;"industriesId":"经营产品",<BR>
     * &nbsp;"industriesExplai":"经营产品说明",<BR>
     * &nbsp;"bankcardTurnover":"预计月平均银行卡营业额,单位：元（请四舍五入保留整数）",<BR>
     * &nbsp;"salesslipTurnover":"预计每张签购单平均交易额,单位：元（请四舍五入保留整数）",<BR>
     * &nbsp;"lfqTurnover":"预计月平均乐百分营业额,单位：元（请四舍五入保留整数",<BR>
     * &nbsp;"channelsId":"渠道商编号",<BR>
     * &nbsp;"bankName":"商户账户开户行名称",<BR>
     * &nbsp;"bankNum":"商户账户开户行号",<BR>
     * &nbsp;"holderName":"商户账户户名",<BR>
     * &nbsp;"cardNum":"商户账户银行账号",<BR>
     * &nbsp;"unionpayMerchantNum":"银联商户号",<BR>
     * &nbsp;"parentName":"母公司名称",<BR>
     * &nbsp;"level":"证件类型,1：新营业执照；2：旧的营业执照",<BR>
     * &nbsp;"remark":"备注"<BR>
     * &nbsp;}<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/getMerInfo",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getMerInfo(@RequestBody String jsonStr) {


        return null;
    }
}
