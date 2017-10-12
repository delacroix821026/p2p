package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.dao.*;
import com.newland.financial.p2p.domain.entity.*;
import com.newland.financial.p2p.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 对产品进行操作的service类.
 * @author cendaijuan
 * */
@Service
public class ProductServiceImpl implements IProductService {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**Dao层对象.*/
    @Autowired
    private IProductDao productDao;
    /**Dao层对象.*/
    @Autowired
    private IInterestDao interestDao;
    /**Dao层对象.*/
    @Autowired
    private ICutMethodDao cutMethodDao;
    /**Dao层对象.*/
    @Autowired
    private IOrganizationDao organizationDao;
    /**Dao层对象.*/
    @Autowired
    private IOrgNegativeDao orgNegativeDao;

    /**
     * 查询所有产品.
     * @return List返回所有产品
     */
    public List getProductList() {
        return productDao.findAll();
    }
    /**
     * 获取指定产品的信息.
     *@param id String产品编号
     *@return IProduct返回指定的产品
     * */
    public IProduct getProduct(String id) {
        AbstractProduct product = productDao.findById(id);
        logger.info("service--product-------:" + product);
        product.setInterestList(interestDao.findByProId((id)));
        product.setCutMethodList(cutMethodDao.selectCutMethod(id));
        product.setOrganizationsList(organizationDao.selectOrganizationList(id));
        return product;
    }
    /**
     * 插入新产品.
     *@param jsonStr json字符串
     *@return boolean 返回布尔值
     * */
    public boolean insertProduct(String jsonStr) {
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        Product pro = paramJSON.toJavaObject(Product.class);
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
        product.setPositiveOrNegative(positiveOrNegative);

        List<Interest> list = new ArrayList<Interest>();
        String[] interestList = paramJSON.getObject("interestList",String[].class);
        for (int i = 0; i < interestList.length; i++){
            String str = interestList[i];
            JSONObject ob = JSON.parseObject(str);
            Interest in = new Interest();
            Integer times = Integer.parseInt(ob.getString("times"));
            BigDecimal intRate = new BigDecimal(ob.getString("intRate"));
            in.setTimes(times);
            in.setIntRate(intRate);
            in.setIProId(proId);
            list.add(in);
        }

        List<CutMethod> list2 = new ArrayList<CutMethod>();
        String[] cutMhds = paramJSON.getObject("cutMhds",String[].class);
        for (int i = 0; i < cutMhds.length; i++){
            String str = cutMhds[i];
            JSONObject ob = JSON.parseObject(str);
            CutMethod cutMethod = new CutMethod();
            String cutMhd = ob.getString("cutMhd");
            cutMethod.setProId(proId);
            cutMethod.setCutMhd(cutMhd);
            list2.add(cutMethod);
        }
        Boolean b2 = null;
        String[] orgs = paramJSON.getObject("orgs",String[].class);
        if ("1".equals(positiveOrNegative)) {
            List<Organization> list1 = new ArrayList<Organization>();
            for (int i = 0; i < orgs.length; i++){
                String str = orgs[i];
                JSONObject ob = JSON.parseObject(str);
                Organization org = new Organization();
                String organization = ob.getString("organization");
                String orgaName = ob.getString("orgaName");
                String parentId = ob.getString("parentId");
                String orgStus = ob.getString("orgStus");
                org.setProId(proId);
                org.setOrganization(organization);
                org.setOrgaName(orgaName);
                org.setParentId(parentId);
                org.setOrgStus(orgStus);
                list1.add(org);
            }
            b2 = organizationDao.insertOrganizationList(list1); //将该产品对应可查看到的机构插入正选表中
        } else if ("2".equals(positiveOrNegative)){
            List<OrgNegative> list1 = new ArrayList<OrgNegative>();
            for (int i = 0; i < orgs.length; i++){
                String str = orgs[i];
                JSONObject ob = JSON.parseObject(str);
                OrgNegative org = new OrgNegative();
                String organization = ob.getString("organization");
                String orgaName = ob.getString("orgaName");
                String parentId = ob.getString("parentId");
                String orgStus = ob.getString("orgStus");
                org.setProId(proId);
                org.setOrganization(organization);
                org.setOrgaName(orgaName);
                org.setParentId(parentId);
                org.setOrgStus(orgStus);
                list1.add(org);
            }
            b2 = orgNegativeDao.insertOrgNegativeList(list1); //将该产品对应可查看到的机构插入反选表中
        } else {
            b2 = false;
        }
        Boolean b1 = interestDao.insertInterest(list); //将产品各分期利率插入利率表中
        Boolean b3 = cutMethodDao.insertCutMethod(list2); //将该产品对应的扣款方式插入表中
        Boolean b4 = productDao.insertProduct(product); //将产品信息插入产品表中
        if (b1 && b2 && b3 && b4) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 更改产品的上下架状态.
     *@param proId 产品编码
     *@param putAndDown 上下架状态
     *@return boolean 返回布尔值
     * */
    public boolean updatePutAndDown(String proId, String putAndDown) {
        return productDao.updatePutAndDown(proId,putAndDown);
    }

}
