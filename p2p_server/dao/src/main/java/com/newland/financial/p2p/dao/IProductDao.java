package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Product;

import java.util.List;
/**
 * 定义对产品进行增删改查的接口.
 * @author Mxia
 * */
public interface IProductDao {
    /**
     * 增加产品.
     * @param product Product产品对象
     * @return  boolean插入成功返回true,失败false
     * */
    boolean insertProduct(Product product);
    /**
     * 删除产品.
     * @param proId String产品编号
     * @return  boolean删除成功返回true,失败false
     * */
    boolean deleteProduct(String proId);
    /**
     * 修改产品信息.
     * @param product Product产品对象
     * @return  boolean更新成功返回true,失败false
     * */
    boolean updateProduct(Product product);
    /**
     * 查看产品信息.
     * @param proId String产品编号
     * @return  Product返回对应编号的产品信息对象
     * */
    Product findById(String proId);
    /**
     * 查询全部产品.
     * @return  List返回所有产品信息的集合
     * */
    List<Product> findAll();

}
