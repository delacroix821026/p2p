package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.IProduct;

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
    /**
     * 更改产品的上下架状态.
     *@param proId 产品编码
     *@param putAndDown 上下架状态
     *@return boolean 返回布尔值
     * */
    boolean updatePutAndDown(String proId, String putAndDown);
    /**
     * 查看产品编号是否存在.
     * @param id 产品编码
     * @return producr
     */
    IProduct findProduct(String id);

     /**
     * 更新产品信息.
     * @param jsonStr   json字符串
     * @return  true:更新成功,false:更新失败
     */
    boolean updateProdInfo(String jsonStr);

    /**
     * 查询产品列表.
     * @param jsonStr   查询条件json字符串
     * @return Object
     */
     Object getProdList(String jsonStr);
    /**
     *App根据角色和机构号查询产品列表.
     * @param role 角色
     * @param organization 机构号
     * @param page 当前页
     * @param count 每页条数
     * @return Object 产品集合
     */
    Object findAppProducts(String role, String organization, Integer page, Integer count);
    /**
     *查看用户各产品的贷款状态.
     * @param id 用户编号
     * @param page 当前页
     * @param count 每页显示条数
     * @return Object
     */
    Object checkCustomerFlowDebitStus(String id, Integer page, Integer count);
}
