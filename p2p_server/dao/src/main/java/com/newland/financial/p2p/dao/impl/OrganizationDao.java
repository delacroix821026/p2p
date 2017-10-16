package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrganizationDao;
import com.newland.financial.p2p.domain.entity.Organization;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Organization Dao层实现.
 * @author Mxia
 */
@Repository
public class OrganizationDao extends
        MybatisBaseDao<Organization> implements IOrganizationDao {
    /**
     * 根据产品编号删除机构表里有关该产品的记录.
     * @param proId 产品编号
     * @return true or false
     */
    public Boolean deleteOrganization(String proId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("proId", proId);
        return super.deletes("deleteByProperty", map);
    }
    /**
     * 将一个Organization的集合循环插入数据库表中.
     * @param list 存放Organization的集合
     * @return true or false
     */
    public Boolean insertOrganizationList(List<Organization> list) {
        return super.insertObj("insertOrganizations", list);
    }
    /**
     * 根据产品编码返回一个机构号列表.
     * @param proId 产品编码
     * @return  List
     */
    public List<Organization> selectOrganizationList(String proId) {
        Map map = new HashMap();
        map.put("proId", proId);
        return select("selectByProperty", map);
    }
}
