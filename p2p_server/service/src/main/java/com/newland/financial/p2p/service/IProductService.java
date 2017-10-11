package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.IProduct;
import com.newland.financial.p2p.domain.entity.Product;

import java.util.List;
/**
 * 定义对产品进行操作的service接口.
 * @author cendaijuan
 * */
public interface IProductService {
    /**
     * 查询所有产品.
     * @return List返回所有产品
     */
    List<IProduct> getProductList();
    /**
     * 获取指定产品的信息.
     *@param id String产品编号
     *@return IProduct返回指定的产品
     * */
    IProduct getProduct(String id);
    /**
     * 插入新产品.
     *@param jsonStr json字符串
     *@return boolean 返回布尔值
     * */
    boolean insertProduct(String jsonStr);
}
