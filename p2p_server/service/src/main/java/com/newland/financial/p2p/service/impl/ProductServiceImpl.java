package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.dao.IOrganizationDao;
import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.Product;
import com.newland.financial.p2p.domain.entity.Organization;
import com.newland.financial.p2p.domain.entity.Interest;
import com.newland.financial.p2p.domain.entity.IProduct;
import com.newland.financial.p2p.domain.entity.AbstractProduct;
import com.newland.financial.p2p.service.IProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * 对产品进行操作的service类.
 *
 * @author cendaijuan
 */
@Log4j
@Service
public class ProductServiceImpl implements IProductService {
    /**
     * Dao层对象.
     */
    @Autowired
    private IProductDao productDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IInterestDao interestDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IOrganizationDao organizationDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;

    /**
     * 查询所有产品.
     *
     * @return List返回所有产品
     */
    public List getProductList() {
        return productDao.findAll();
    }

    /**
     * 获取指定产品的信息.
     *
     * @param id String产品编号
     * @return IProduct返回指定的产品
     */
    public IProduct getProduct(String id) {
        AbstractProduct product = productDao.findById(id);
        product.setOrganizationsList(organizationDao.selectOrganizationList(id));
        log.debug("service--product-------:" + product);
        product.setInterestList(interestDao.findByProId((id)));
        return product;
    }

    /**
     * 插入新产品.
     *
     * @param jsonStr json字符串
     * @return boolean 返回布尔值
     */
    public boolean insertProduct(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        Product product = new Product();
        String proId = paramJSON.getString("proId");
        String proName = paramJSON.getString("proName");
        BigDecimal proLmt = new BigDecimal(paramJSON.getString("proLmt"));
        String proNameOperator = paramJSON.getString("proNameOperator");
        String sponsor = paramJSON.getString("sponsor");
        String sprProName = paramJSON.getString("sprProName");
        BigDecimal maxLmt = new BigDecimal(paramJSON.getString("maxLmt"));
        String role = paramJSON.getString("role");
        String advanceRepay = paramJSON.getString("advanceRepay");
        String formula = paramJSON.getString("formula");
        BigDecimal latefee = new BigDecimal(paramJSON.getString("latefee"));
        String interestMhd = paramJSON.getString("interestMhd");
        String poundage = paramJSON.getString("poundage");
        String isLatefee = paramJSON.getString("isLatefee");
        String repayMhd = paramJSON.getString("repayMhd");
        String cutMhd = paramJSON.getString("cutMhd");
        String positiveOrNegative = paramJSON.getString("positiveOrNegative");
        product.setProId(proId);
        product.setProName(proName);
        product.setProLmt(proLmt);
        product.setProNameOperator(proNameOperator);
        product.setSponsor(sponsor);
        product.setSprProName(sprProName);
        product.setCreateTime(new Date());
        product.setMaxLmt(maxLmt);
        product.setRole(role);
        product.setAdvanceRepay(advanceRepay);
        product.setFormula(formula);
        product.setLatefee(latefee);
        product.setInterestMhd(interestMhd);
        product.setPoundage(poundage);
        product.setIsLatefee(isLatefee);
        product.setRepayMhd(repayMhd);
        product.setCutMhd(cutMhd);
        product.setPositiveOrNegative(positiveOrNegative);

        List<Interest> list = new ArrayList<Interest>();
        String[] interestList = paramJSON.getObject("interestList", String[].class);
        for (int i = 0; i < interestList.length; i++) {
            String str = interestList[i];
            JSONObject ob = JSON.parseObject(str);
            Interest in = new Interest();
            Integer times = Integer.parseInt(ob.getString("times"));
            in.setTimes(times);
            in.setIProId(proId);
            list.add(in);
        }

        Boolean b2 = false;
        String[] orgs = paramJSON.getObject("orgs", String[].class);
        List<Organization> list1 = new ArrayList<Organization>();
        for (int i = 0; i < orgs.length; i++) {
            String str = orgs[i];
            JSONObject ob = JSON.parseObject(str);
            Organization org = new Organization();
            String organization = ob.getString("organization");
            org.setProId(proId);
            org.setOrganization(organization);
            org.setPositiveOrNegative(positiveOrNegative);
            list1.add(org);
        }
        b2 = organizationDao.insertOrganizationList(list1); //将该产品对应可查看到的机构插入机构表中
        Boolean b1 = interestDao.insertInterest(list); //将产品各分期利率插入利率表中
        Boolean b3 = productDao.insertProduct(product); //将产品信息插入产品表中
        log.debug("机构插入b2---：" + b2 + ",分期插入b1---：" + b1 + ",产品插入b3----：" + b3);
        return b1 && b2 && b3;
    }

    /**
     * 更改产品的上下架状态.
     *
     * @param proId      产品编码
     * @param putAndDown 上下架状态
     * @return boolean 返回布尔值
     */
    public boolean updatePutAndDown(String proId, String putAndDown) {
        Product product = productDao.findById(proId);
        //如果该产品是已下架状态则不允许在上架
        if ("3".equals(product.getPutAndDown())) {
            return false;
        }
        return productDao.updatePutAndDown(proId, putAndDown);
    }

    /**
     * 查看产品编号是否存在.
     *
     * @param id 产品编码
     * @return producr
     */
    public IProduct findProduct(String id) {
        AbstractProduct product = productDao.findById(id);
        return product;
    }


    /**
     * 更新产品信息.
     *
     * @param jsonStr json字符串
     * @return true:更新成功,false:更新失败
     */
    public boolean updateProdInfo(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        Product pro = paramJSON.toJavaObject(Product.class);
        Product product = new Product();

        String proId = paramJSON.getString("proId");
        if (proId == null || proId == "") {
            return false;
        }
        product.setProId(proId);

        String proName = paramJSON.getString("proName");
        if (proName == null || proName == "") {
            return false;
        }
        product.setProName(proName);

        String proLmtStr = paramJSON.getString("proLmt");
        String maxLmtStr = paramJSON.getString("maxLmt");
        String latefeeStr = paramJSON.getString("latefee");
        if (!"".equals(proLmtStr)) {
            product.setProLmt(new BigDecimal(proLmtStr));
        }
        if (!"".equals(maxLmtStr)) {
            product.setMaxLmt(new BigDecimal(maxLmtStr));
        }
        if (!"".equals(latefeeStr)) {
            product.setLatefee(new BigDecimal(latefeeStr));
        }
        String positiveOrNegative = paramJSON.getString("positiveOrNegative");

        product.setProNameOperator(paramJSON.getString("proNameOperator"));
        product.setSponsor(paramJSON.getString("sponsor"));
        product.setSprProName(paramJSON.getString("sprProName"));
        product.setRole(paramJSON.getString("role"));
        product.setAdvanceRepay(paramJSON.getString("advanceRepay"));
        product.setFormula(paramJSON.getString("formula"));
        product.setInterestMhd(paramJSON.getString("interestMhd"));
        product.setPoundage(paramJSON.getString("poundage"));
        product.setIsLatefee(paramJSON.getString("isLatefee"));
        product.setRepayMhd(paramJSON.getString("repayMhd"));
//        product.setPositiveOrNegative(paramJSON.getString("positiveOrNegative"));
        product.setCutMhd(paramJSON.getString("cutMhd"));
        product.setLastModiTime(new Date());

        //获取产品利率信息
        List<Interest> interestList = new ArrayList<Interest>();
        String[] interestJson = paramJSON.getObject("interestList", String[].class);
        for (String s : interestJson) {
            JSONObject ob = JSON.parseObject(s);
            Interest interest = ob.toJavaObject(Interest.class);
            interest.setIProId(proId);
            interest.setIProName(proName);
            interestList.add(interest);
        }
        //获取机构信息
        List<Organization> orgList = new ArrayList<Organization>();
        String[] orgJson = paramJSON.getObject("orgs", String[].class);
        for (String s : orgJson) {
            JSONObject ob = JSON.parseObject(s);
            Organization organization = ob.toJavaObject(Organization.class);
            organization.setProId(proId);
            organization.setPositiveOrNegative(positiveOrNegative);
            orgList.add(organization);
        }

        //更新产品信息
        if (!productDao.updateProduct(product)) {
            log.debug("----------------------------------------更新产品信息失败");
            return false;
        }
        //更新利率信息
        List<Interest> interestTempList = interestDao.findByProId(proId);
        log.debug("--------判断表中是否存在指定产品的利率信息-------" + interestDao.findByProId(proId).size());
        if (interestTempList != null && interestTempList.size() > 0) { //判断表中是否存在指定产品的利率信息
            interestDao.deleteInterestByProId(proId);
        }
        if (!interestDao.insertInterest(interestList)) {
            log.debug("----------------------------------------插入利率失败");
            return false;
        }

        //更新机构信息
        //删除原有产品-机构信息
        List<Organization> orgTempList = organizationDao.selectOrganizationList(proId);
        if (orgTempList != null && orgTempList.size() > 0) {
            organizationDao.deleteOrganization(proId);
        }
        //插入新的产品-机构信息
        if (!organizationDao.insertOrganizationList(orgList)) {
            log.debug("------------------------------插入新的产品-机构信息失败");
            return false;
        }
        return true;
    }


    /**
     * 查询产品列表.
     *
     * @param jsonStr 查询条件json字符串
     * @return 产品分页信息
     */
    public Object getProdList(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);

        //获取分页信息
        Integer page = paramJSON.getInteger("page");
        Integer count = paramJSON.getInteger("count");
        if (page == null || page < 1) {
            page = 1;
        }
        if (count == null || count < 1) {
            count = 5;
        }
        //开始分页
        PageHelper.startPage(page, count);
        //获取产品信息
        String role = paramJSON.getString("role");
        String proId = paramJSON.getString("proId");
        String proName = paramJSON.getString("proName");
        String sponsor = paramJSON.getString("sponsor");
        //转换日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long createTimeBeg = paramJSON.getLong("createTimeBeg");
        Long createTimeEnd = paramJSON.getLong("createTimeEnd");
        String begTime = null;
        String endTime = null;
        if (createTimeBeg != null) {
            Date begTimeDate = new Date(createTimeBeg);
            begTime = sdf.format(begTimeDate);
            log.debug("---------------------------------" + begTime);
        }
        if (createTimeEnd != null) {
            Date endTimeDate = new Date(createTimeEnd);
            endTime = sdf.format(endTimeDate);
            log.debug("---------------------------------" + endTime);
        }
        Map<String, Object> reqMap = new HashMap<String, Object>();
        log.debug("*****************************role:" + role);
        log.debug("*****************************proId:" + proId);
        log.debug("*****************************proName:" + proName);
        if (!"".equals(role)) {
            reqMap.put("role", role);
        }
        if (!"".equals(proId)) {
            reqMap.put("proId", proId);
        }
        if (!"".equals(proName)) {
            reqMap.put("proName", proName);
        }
        if (!"".equals(sponsor)) {
            reqMap.put("sponsor", sponsor);
        }
        reqMap.put("begTime", begTime);
        reqMap.put("endTime", endTime);

        List<Product> productList = new ArrayList<Product>();
        productList = productDao.findAll(reqMap);
        PageInfo<Product> pageInfo = new PageInfo<Product>(productList);

        Map<String, Object> repJson = new HashMap<String, Object>();
        repJson.put("total", pageInfo.getSize());
        repJson.put("rows", pageInfo.getList());

        return pageInfo;
    }

    /**
     * App根据角色和机构号查询产品列表.
     *
     * @param role         角色
     * @param organization 机构号
     * @param page         当前页
     * @param count        每页显示的条数
     * @return 产品集合
     */
    public Object findAppProducts(String role, String organization, Integer page, Integer count) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (count == null || count < 1) {
            count = 5;
        }
        //开始分页
        PageHelper.startPage(page, count);
        List<Product> productList = new ArrayList<Product>();
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("role", role);
        reqMap.put("organization", organization);
        productList = productDao.findAppProducts(reqMap);
        PageInfo<Product> pageInfo = new PageInfo<Product>(productList);
        return pageInfo;
    }

    /**
     * 查看用户各产品的贷款状态.
     *
     * @param id 用户编号
     * @param page 当前页
     * @param count 每页显示条数
     * @return 包含产品status的集合
     */
    public Object checkCustomerFlowDebitStus(String id, Integer page, Integer count) {
        //debitAndCreditDao.updateStusToThree(); //将数据库内申请时间超过15天的贷款单状态改为拒绝
        if (page == null || page < 1) {
            page = 1;
        }
        if (count == null || count < 1) {
            count = 5;
        }
        //开始分页
        PageHelper.startPage(page, count);
        List<Product> list = new ArrayList<Product>();
        list = productDao.findCustomerFlowDebitStus(id);
        PageInfo<Product> pageInfo = new PageInfo<Product>(list);
        return pageInfo;
    }
}
