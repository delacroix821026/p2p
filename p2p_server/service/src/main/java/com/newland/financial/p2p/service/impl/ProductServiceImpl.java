package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.dao.*;
import com.newland.financial.p2p.domain.entity.*;
import com.newland.financial.p2p.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String postiveOrNegative = product.getPositiveOrNegative();
        if (postiveOrNegative.equals("1")) {
            product.setOrganizationsList(organizationDao.selectOrganizationList(id));
        } else {
            product.setOrganizationsList(orgNegativeDao.selectOrgNegativeList(id));
        }
        logger.info("service--product-------:" + product);
        product.setInterestList(interestDao.findByProId((id)));
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
        String cutMhd = paramJSON.getString("cutMhd");
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
        product.setCutMhd(cutMhd);

        List<Interest> list = new ArrayList<Interest>();
        String[] interestList = paramJSON.getObject("interestList",String[].class);
        for (int i = 0; i < interestList.length; i++){
            String str = interestList[i];
            JSONObject ob = JSON.parseObject(str);
            Interest in = new Interest();
            Integer times = Integer.parseInt(ob.getString("times"));
            in.setTimes(times);
            in.setIProId(proId);
            list.add(in);
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
                org.setProId(proId);
                org.setOrganization(organization);
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
                org.setProId(proId);
                org.setOrganization(organization);
                list1.add(org);
            }
            b2 = orgNegativeDao.insertOrgNegativeList(list1); //将该产品对应可查看到的机构插入反选表中
        } else {
            b2 = false;
        }
        Boolean b1 = interestDao.insertInterest(list); //将产品各分期利率插入利率表中
        Boolean b3 = productDao.insertProduct(product); //将产品信息插入产品表中
        if (b1 && b2 && b3) {
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
    /**
     * 查看产品编号是否存在.
     * @param id 产品编码
     * @return producr
     */
    public IProduct findProduct(String id) {
        AbstractProduct product = productDao.findById(id);
        return product;
    }

	/**
     * 更新产品信息.
     * @param jsonStr   json字符串
     * @return  true:更新成功,false:更新失败
     * &nbsp;&nbsp;"proId":"产品编号",<BR>
     *                       &nbsp;&nbsp;"proId":"产品编号",<BR>
     *                       &nbsp;&nbsp;"proName":"产品名称",<BR>
     *                       &nbsp;&nbsp;"proNameOperator":"产品别名",<BR>
     *                       &nbsp;&nbsp;"sponsor":"资方名称"，<BR>
     *                       &nbsp;&nbsp;"sprProName":"资方产品名称",<BR>
     *                       &nbsp;&nbsp;"proLmt":"最低产品限额",<BR>
     *                       &nbsp;&nbsp;"maxLmt":"最高产品限额",<BR>
     *                       &nbsp;&nbsp;"repayMhd":"还款方式",<BR>
     *                       &nbsp;&nbsp;"interestMhd":"利息方式",<BR>
     *                       &nbsp;&nbsp;"advanceRepay":"是否提前还款：1是，2否",<BR>
     *                       &nbsp;&nbsp;"poundage":"提前还款是否收取手取费：1是，2否",<BR>
     *                       &nbsp;&nbsp;"formula":"手续费公式",<BR>
     *                       &nbsp;&nbsp;"isLatefee":"是否收取滞纳金,1是，2否",<BR>
     *                       &nbsp;&nbsp;"latefee":"逾期滞纳金",<BR>
     *                       &nbsp;&nbsp;"role":"角色：1管理员，2操作员，0全部",<BR>
     *                       &nbsp;&nbsp;"cutMhd":["1银行代扣"，"2自主还款"],<BR>
     *                       &nbsp;&nbsp;"interest":[{"time":"分期1","intRate":"利率1"},{"time":"分期2","intRate":"利率2"}],<BR>
     *                       &nbsp;&nbsp;"orgs":[{"organization":机构号,"orga_name":机构名,"parentid":父机构号,"orgstus":状态}]<BR>
     */
    public boolean updateProdInfo(String jsonStr){
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        //获取产品信息
        Product product = new Product();
        String proId = paramJSON.getString("proId");
        String proName = paramJSON.getString("proName");
        String proNameOperator = paramJSON.getString("proNameOperator");
        String sponsor = paramJSON.getString("sponsor");
        String sprProName = paramJSON.getString("sprProName");
        BigDecimal proLmt = new BigDecimal(paramJSON.getString("proLmt"));
        BigDecimal maxLmt = new BigDecimal(paramJSON.getString("maxLmt"));
        String repayMhd = paramJSON.getString("repayMhd");
        String interestMhd = paramJSON.getString("interestMhd");
        String advanceRepay = paramJSON.getString("advanceRepay");
        String poundage = paramJSON.getString("poundage");
        String formula = paramJSON.getString("formula");
        String isLatefee = paramJSON.getString("isLatefee");
        BigDecimal latefee = new BigDecimal(paramJSON.getString("latefee"));
        String role = paramJSON.getString("role");

        product.setProId(proId);
        product.setProName(proName);
        product.setProNameOperator(proNameOperator);
        product.setSponsor(sponsor);
        product.setSprProName(sprProName);
        product.setProLmt(proLmt);
        product.setMaxLmt(maxLmt);
        product.setRepayMhd(repayMhd);
        product.setInterestMhd(interestMhd);
        product.setAdvanceRepay(advanceRepay);
        product.setPoundage(poundage);
        product.setFormula(formula);
        product.setIsLatefee(isLatefee);
        product.setLatefee(latefee);
        product.setRole(role);
        product.setLastModiTime(new Date());

        //获取产品利率信息
        List<Interest> interestList = new ArrayList<Interest>();
        String[] interestJson = paramJSON.getObject("interest",String[].class);
        for(String s : interestJson){
            JSONObject ob = JSON.parseObject(s);
            Interest interest = ob.toJavaObject(Interest.class);
            interestList.add(interest);
        }
        //获取机构信息
        List<Organization> orgList = new ArrayList<Organization>();
        String[] orgJson = paramJSON.getObject("orgs",String[].class);
        for(String s : interestJson){
            JSONObject ob = JSON.parseObject(s);
            Organization organization = ob.toJavaObject(Organization.class);
            orgList.add(organization);
        }
        //扣款方式
//        String[] cutMhd = paramJSON.getObject("cutMhd",String[].class);
        //清除原有的产品利率信息

        return  true;
    }

    /**
     * 查询产品列表.
     * @param jsonStr   查询条件json字符串
     * @return 产品分页信息
     */
    public Object getProdList(String jsonStr){
        JSONObject paramJSON = JSON.parseObject(jsonStr);

        //获取分页信息
        Integer page = paramJSON.getInteger("page");
        Integer count = paramJSON.getInteger("count");
        if(page == null || page <1){
            page = 1;
        }
        if(count == null || count < 1){
            count = 5;
        }
        //开始分页
        PageHelper.startPage(page,count);
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
        if(createTimeBeg != null){
            Date begTimeDate = new Date(createTimeBeg);
            begTime = sdf.format(begTimeDate);
            System.out.println("---------------------------------"+begTime);
        }
        if(createTimeEnd != null){
            Date endTimeDate = new Date(createTimeEnd);
            endTime = sdf.format(endTimeDate);
            System.out.println("---------------------------------"+endTime);
        }
//        String begTime = "1043078400000";
//        String endTime = "1167494400000";

        Map<String,Object> reqMap = new HashMap<String, Object>();
        reqMap.put("role",role);
        reqMap.put("proId",proId);
        reqMap.put("proName",proName);
        reqMap.put("sponsor",sponsor);
        reqMap.put("begTime",begTime);
        reqMap.put("endTime",endTime);

        List<Product> productList = new ArrayList<Product>();
//        if(proId != null){
//
//        }else {
//            productList = productDao.findAll(reqMap);
//        }
        productList = productDao.findAll(reqMap);
        PageInfo<Product> pageInfo = new PageInfo<Product>(productList);

        Map<String,Object> repJson = new HashMap<String, Object>();
        repJson.put("total",pageInfo.getSize());
        repJson.put("rows",pageInfo.getList());

        return pageInfo;
    }
}
