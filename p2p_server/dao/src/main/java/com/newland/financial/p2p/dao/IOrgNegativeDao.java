package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.OrgNegative;

import java.util.List;

/**
 * Dao层接口.
 * @author Mxia
 */
public interface IOrgNegativeDao {
    /**
     * 根据产品编号删除机构表里有关该产品的记录.
     * @param proId 产品编号
     * @return true or false
     */
    Boolean deleteOrgNegative(String proId);
    /**
     * 将一个OrgNegative的集合循环插入数据库表中.
     * @param list 存放OrgNegative的集合
     * @return true or false
     */
    Boolean insertOrgNegativeList(List<OrgNegative> list);
    /**
     * 根据产品编码返回一个机构号列表.
     * @param proId 产品编码
     * @return  List
     */
    List<OrgNegative> selectOrgNegativeList(String proId);
}
