package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Organization;

import java.util.List;

/**
 * Organization Dao层接口.
 * @author Mxia
 */
public interface IOrganizationDao {
    /**
     * 根据产品编号删除机构表里有关该产品的记录.
     * @param proId 产品编号
     * @return true or false
     */
    Boolean deleteOrganization(String proId);
    /**
     * 将一个Organization的集合循环插入数据库表中.
     * @param list 存放Organization的集合
     * @return true or false
     */
    Boolean insertOrganizationList(List<Organization> list);
    /**
     * 根据产品编码返回一个机构号列表.
     * @param proId 产品编码
     * @return  List
     */
    List<Organization> selectOrganizationList(String proId);
}
