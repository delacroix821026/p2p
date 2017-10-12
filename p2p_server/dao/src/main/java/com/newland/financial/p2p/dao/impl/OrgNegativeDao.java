package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrgNegativeDao;
import com.newland.financial.p2p.domain.entity.OrgNegative;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Dao层接口实现.
 * @author Mxia
 */
@Repository
public class OrgNegativeDao extends
        MybatisBaseDao<OrgNegative> implements IOrgNegativeDao {
    /**
     * 根据产品编号删除机构表里有关该产品的记录.
     * @param proId 产品编号
     * @return true or false
     */
    public Boolean deleteOrgNegative(String proId) {
        return super.deletes("deleteByProperty",proId);
    }
    /**
     * 将一个OrgNegative的集合循环插入数据库表中.
     * @param list 存放OrgNegative的集合
     * @return true or false
     */
    public Boolean insertOrgNegativeList(List<OrgNegative> list) {
        return super.insertObj("insertOrgNegativeList", list);
    }
    /**
     * 根据产品编码返回一个机构号列表.
     * @param proId 产品编码
     * @return  List
     */
    public List<OrgNegative> selectOrgNegativeList(String proId) {
        return super.select("selectByProperty",proId);
    }
}
