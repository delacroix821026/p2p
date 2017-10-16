package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.IProduct;
import com.newland.financial.p2p.domain.entity.Product;
import com.newland.financial.p2p.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author cendaijuan
 * */
@Controller
@RequestMapping("/ProductController")
public class ProductController {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**productService对象.*/
    @Autowired
    private IProductService productService;

    /**
     * 获取所有产品信息.
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List;<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ proId:xx,proName:xx,proLmt:xx,payDate:xx,interestList:xx},<BR>
     *  &nbsp;&nbsp;{ proId:xx,proName:xx,proLmt:xx,payDate:xx,interestList:xx}<BR>
     *  }
     * */
    /*@ResponseBody
    @RequestMapping(value = "/GetProductList",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getProductList() {
        logger.info("ProductController GetProductList");
        return productService.getProductList();
    }*/

    /**
     * 获取指定产品信息.
     * @param jsonStr 接受的json字符串,{"productId":"产品编号"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.domain.entity.Product;<BR>
     * {<BR>
     *  &nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;"proName":"管理员所看到的产品名称",<BR>
     *  &nbsp;&nbsp;"proLmt":最低贷款额度,<BR>
     *  &nbsp;&nbsp;"payDate":null,<BR>
     *  &nbsp;&nbsp;"proInterest":null,<BR>
     *  &nbsp;&nbsp;"proNameOperator":"操作员可视产品名称",<BR>
     *  &nbsp;&nbsp;"sponsor":"出资方",<BR>
     *  &nbsp;&nbsp;"sprProName":"资方产品名称",<BR>
     *  &nbsp;&nbsp;"createTime":创建时间,<BR>
     *  &nbsp;&nbsp;"lastModiTime":最后修改时间,<BR>
     *  &nbsp;&nbsp;"maxLmt":最大贷款额,<BR>
     *  &nbsp;&nbsp;"role":"角色：1管理员，2操作员，0全部",<BR>
     *  &nbsp;&nbsp;"putAndDown":"1上架，2下架",<BR>
     *  &nbsp;&nbsp;"advanceRepay":"是否允许提前还款：1允许，2不允许",<BR>
     *  &nbsp;&nbsp;"poundage":"提前还款是否收取手续费：1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"formula":"手续费公式，如为空则表示无手续费",<BR>
     *  &nbsp;&nbsp;"isLatefee":"是否收取滞纳金:1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"latefee":"逾期滞纳金额",<BR>
     *  &nbsp;&nbsp;"interestMhd":"利息方式：1固定利息，2浮动利息",<BR>
     *  &nbsp;&nbsp;"repayMhd":"还款方式：1等额本息，2等额本金,3全部",<BR>
     *  &nbsp;&nbsp;"cutMhd":"扣款方式：1银行代扣，2自主还款"<BR>
     *  &nbsp;&nbsp;"interestList":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  &nbsp;&nbsp;"organizationsList":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号",<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"positiveOrNegative":"1正选，1反选"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号",<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"positiveOrNegative":"1正选，1反选"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  }<BR>
     * */
    @ResponseBody
    @RequestMapping(value = "/GetProduct",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getProduct(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String proId = paramJSON.getString("proId");
        logger.info("ProductController GetProductList:proId--" + proId);
        if (proId == null || proId.equals("")) {
            return "proId不可为空";
        } else {
            IProduct product = productService.getProduct(proId);
            logger.info(product.toString());
            return product;
        }
    }
    /**
     * 新增产品.
     * @param jsonStr 接受的json字符串:<BR>
     * {<BR>
     *  &nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;"proName":"管理员所看到的产品名称",<BR>
     *  &nbsp;&nbsp;"proLmt":最低贷款额度,<BR>
     *  &nbsp;&nbsp;"interestList":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  &nbsp;&nbsp;"proNameOperator":"操作员可视产品名称",<BR>
     *  &nbsp;&nbsp;"sponsor":"出资方",<BR>
     *  &nbsp;&nbsp;"sprProName":"资方产品名称",<BR>
     *  &nbsp;&nbsp;"maxLmt":最大贷款额,<BR>
     *  &nbsp;&nbsp;"role":"角色：1管理员，2操作员，0全部",<BR>
     *  &nbsp;&nbsp;"orgs":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  &nbsp;&nbsp;"repayMhd":"还款方式：1等额本息，2等额本金,3全部",<BR>
     *  &nbsp;&nbsp;"interestMhd":"利息方式：1固定利息，2浮动利息",<BR>
     *  &nbsp;&nbsp;"cutMhd":"扣款方式：1银行代扣，2自主还款",<BR>
     *  &nbsp;&nbsp;"advanceRepay":"是否允许提前还款：1允许，2不允许",<BR>
     *  &nbsp;&nbsp;"poundage":"提前还款是否收取手续费：1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"formula":"手续费公式，如为空则表示无手续费",<BR>
     *  &nbsp;&nbsp;"isLatefee":"是否收取滞纳金:1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"latefee":"逾期滞纳金额",<BR>
     *  &nbsp;&nbsp;"positiveOrNegative":"1正选，1反选",<BR>
     *  }<BR>
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： Boolean;<BR>
     * {<BR>
     *  &nbsp;&nbsp;true or flase<BR>
     *  }
     * */
    @ResponseBody
    @RequestMapping(value = "/InsertProduct", method = {RequestMethod.POST, RequestMethod.GET})
    public Object insertProduct(@RequestBody String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        logger.info("jsonStr：" + jsonStr);
        String proId = paramJSON.getString("proId");
        if (productService.findProduct(proId) != null) {
            return "已存在该产品编号";
        }
        return productService.insertProduct(jsonStr);
    }
    /**
     *更新产品信息.
     * @param jsonStr    请求参数：<BR>
     *  {<BR>
     *  &nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;"proName":"管理员所看到的产品名称",<BR>
     *  &nbsp;&nbsp;"proLmt":最低贷款额度,<BR>
     *  &nbsp;&nbsp;"interestList":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"times":分期数<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  &nbsp;&nbsp;"proNameOperator":"操作员可视产品名称",<BR>
     *  &nbsp;&nbsp;"sponsor":"出资方",<BR>
     *  &nbsp;&nbsp;"sprProName":"资方产品名称",<BR>
     *  &nbsp;&nbsp;"maxLmt":最大贷款额,<BR>
     *  &nbsp;&nbsp;"role":"角色：1管理员，2操作员，0全部",<BR>
     *  &nbsp;&nbsp;"orgs":[<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;},<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"organization":"机构号"<BR>
     *  &nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *  &nbsp;&nbsp;],<BR>
     *  &nbsp;&nbsp;"repayMhd":"还款方式：1等额本息，2等额本金,3全部",<BR>
     *  &nbsp;&nbsp;"interestMhd":"利息方式：1固定利息，2浮动利息",<BR>
     *  &nbsp;&nbsp;"cutMhd":"扣款方式：1银行代扣，2自主还款",<BR>
     *  &nbsp;&nbsp;"advanceRepay":"是否允许提前还款：1允许，2不允许",<BR>
     *  &nbsp;&nbsp;"poundage":"提前还款是否收取手续费：1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"formula":"手续费公式，如为空则表示无手续费",<BR>
     *  &nbsp;&nbsp;"isLatefee":"是否收取滞纳金:1收取，2不收取",<BR>
     *  &nbsp;&nbsp;"latefee":"逾期滞纳金额",<BR>
     *  &nbsp;&nbsp;"positiveOrNegative":"1正选，1反选",<BR>
     *  }<BR>
     * @return    返回参数：{"Update Sucess"}
     */
    @ResponseBody
    @RequestMapping(value = "/UpdateProd",method = {RequestMethod.POST, RequestMethod.GET})
    public Object updateProd(@RequestBody String jsonStr){
        return productService.updateProdInfo(jsonStr);
    }


    /**
     * 分页查新产品信息.
     * @param jsonStr   请求参数：{"role":"角色:1管理员,2操作员","proId":"产品编号","proName":"产品名称","sponsor":"资方","createTimeBeg":"创建时间(起)","createTimeEnd":"创建时间(止)","page":"当前页","count":"每页记录数"}
     * @return  返回参数：<BR>{<BR>
     *             &nbsp;&nbsp;"total":"总记录数",<BR>
     *             &nbsp;&nbsp;"rows:"[<BR>
     *             &nbsp;&nbsp;&nbsp;{<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proId": "5",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proName": "管理员所看到的产品名称",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proLmt": "最低贷款额度",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"payDate": "产品还款日期",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proInterest": 产品利率,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proNameOperator": 操作员所看到的产品名称,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"sponsor": 出资方,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"sprProName": 出资方产品名称,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"createTime": 1156219870000,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"lastModiTime": 1156219870000,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"maxLmt": 最大贷款额,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"role": "角色",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"putAndDown": "上下架",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"advanceRepay": 是否允许提前还款,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"poundage": 提前还款是否收取手取费1收取，2不收取,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"formula": 手续费公式,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"isLatefee": 是否收取滞纳金:1收取，2不收取,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"latefee": 逾期滞纳金,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"interestMhd": 利息方式,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp; "repayMhd": 还款方式:1等额本息，2等额本金,3全部,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"cutMhd": 扣款方式：1银行代扣，2自主还款,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"interestList": [{"times":分期数},{"times":分期数}]<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"organizationsList":[{"organization":"机构号"},{"organization":"机构号"}]<BR>
     *             &nbsp;&nbsp;&nbsp;}
     */
    @ResponseBody
    @RequestMapping(value = "/GetProdList",method = {RequestMethod.POST, RequestMethod.GET})
    public Object getProdList(@RequestBody String jsonStr){
        return productService.getProdList(jsonStr);

    }
    /**
     * 操作上下架
     * @param jsonStr 接受的json字符串:<BR>
     *{<BR>
     *  &nbsp;&nbsp;"proId":"产品编号",<BR>
     *  &nbsp;&nbsp;"putAndDown":"上下架状态",<BR>
     * }
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： Boolean;<BR>
     * {<BR>
     * &nbsp;&nbsp;true or flase<BR>
     *  }
     */
    @ResponseBody
    @RequestMapping(value = "/PutOrDown",method = {RequestMethod.POST, RequestMethod.GET})
    public Object putOrDown(@RequestBody String jsonStr){
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        logger.info("jsonStr：" + jsonStr);
        String proId = paramJSON.getString("proId");
        String putAndDown = paramJSON.getString("putAndDown");
        if (proId != null && proId != "" && putAndDown != null && putAndDown != "") {
            return productService.updatePutAndDown(proId,putAndDown);
        } else {
            return false;
        }
    }
    /**
     * App根据角色和机构号返回产品列表.
     *@param jsonStr   请求参数：{"role":"角色:1管理员,2操作员","organization":"机构号","page":"当前页","count":"每页记录数"}<BR>
     * @return  返回参数：<BR>{<BR>
     *             &nbsp;&nbsp;"total":"总记录数",<BR>
     *             &nbsp;&nbsp;"rows:"[<BR>
     *             &nbsp;&nbsp;&nbsp;{<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proId": "5",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proName": "管理员所看到的产品名称",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proLmt": "最低贷款额度",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"payDate": "产品还款日期",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proInterest": 产品利率,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"proNameOperator": 操作员所看到的产品名称,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"sponsor": 出资方,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"sprProName": 出资方产品名称,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"createTime": 1156219870000,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"lastModiTime": 1156219870000,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"maxLmt": 最大贷款额,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"role": "角色",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"putAndDown": "上下架",<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"advanceRepay": 是否允许提前还款,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"poundage": 提前还款是否收取手取费1收取，2不收取,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"formula": 手续费公式,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"isLatefee": 是否收取滞纳金:1收取，2不收取,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"latefee": 逾期滞纳金,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"interestMhd": 利息方式,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp; "repayMhd": 还款方式:1等额本息，2等额本金,3全部,<BR>
     *             &nbsp;&nbsp;&nbsp;&nbsp;"cutMhd": 扣款方式：1银行代扣，2自主还款,<BR>
     *             &nbsp;&nbsp;&nbsp;}
     */
    @ResponseBody
    @RequestMapping(value = "/getAppProducts",method = {RequestMethod.POST, RequestMethod.GET})
    public Object getAppProducts(@RequestBody String jsonStr){
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        logger.info("jsonStr：" + jsonStr);
        String role = paramJSON.getString("role");
        //获取分页信息
        Integer page = paramJSON.getInteger("page");
        Integer count = paramJSON.getInteger("count");
        String organization = paramJSON.getString("organization");
        return productService.findAppProducts(role,organization, page, count);
    }
}
