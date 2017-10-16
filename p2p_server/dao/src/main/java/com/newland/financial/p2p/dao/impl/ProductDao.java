package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对产品进行操作的Dao层类.
 * @author Mxia
 * */
@Repository
public class ProductDao extends MybatisBaseDao<Product> implements IProductDao {

    /**
     * 增加产品.
     * @param product Product产品对象
     * @return  boolean插入成功返回true,失败false
     * */
    public boolean insertProduct(Product product) {
        return super.insertSelective(product);
    }

    /**
     * 删除产品.
     * @param proId String产品编号
     * @return  boolean删除成功返回true,失败false
     * */
    public boolean deleteProduct(String proId) {
        return super.deleteByPrimaryKey(proId);
    }

    /**
     * 修改产品信息.
     * @param product Product产品对象
     * @return  boolean更新成功返回true,失败false
     * */
    public boolean updateProduct(Product product) {
        return super.updateByPrimaryKeySelective(product);
    }

    /**
     * 查看产品信息.
     * @param proId String产品编号
     * @return  Product返回对应编号的产品信息对象
     * */
    public Product findById(String proId) {
        return super.selectByPrimaryKey(proId);
    }
    /**
     * 查询全部产品.
     * @return  List返回所有产品信息的集合
     * */
    public List<Product> findAll() {
        return super.selectAll();
    }
    /**
     * 更改产品的上下架状态.
     *@param proId 产品编码
     *@param putAndDown 上下架状态
     *@return true成功
     * */
    public boolean updatePutAndDown(String proId, String putAndDown) {
        Map map = new HashMap();
        map.put("proId", proId);
        map.put("putAndDown", putAndDown);
        return super.update("updatePutAndDown",map);
    }

    /**
     * 有条件分页查询产品信息.
     * @param map 查询条件
     * @return 分页信息
     */
    public List<Product> findAll(Map<String,Object> map){
        return  super.select("findList",map);
    }
    /**
     * App产品列表展示.
     * @param map 查询条件，包含机构号和角色
     * @return 分页信息
     */
    public List<Product> findAppProducts(Map<String, Object> map) {
        return super.select("selectAppPro", map);
    }
}
