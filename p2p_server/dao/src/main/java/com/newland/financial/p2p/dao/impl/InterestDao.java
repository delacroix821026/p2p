package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.domain.entity.Interest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mxia
 */
@Repository
public class InterestDao extends MybatisBaseDao<Interest>
        implements IInterestDao {

    /**
     * 增加利率信息.
     * @param interest Interest对象
     * @return boolean插入成功返回true,失败false
     */
    public boolean insertInterest(Interest interest) {
        return super.insertSelective(interest);
    }
    /**
     * 删除利率信息.
     * @param ittId String利率编号
     * @return boolean删除成功返回true,失败false
     */
    public boolean deleteInterest(Integer ittId) {
        return super.deletes("deleteByProperty",ittId);
    }

    /**
     * 修改利率信息.
     * @param interest Interest对象
     * @return boolean修改成功返回true,失败false
     */
    public boolean updateInterest(final Interest interest) {
        return super.updateByPrimaryKeySelective(interest);
    }

    /**
     * 查看某产品的所有利率信息.
     * @param iProId String产品编号
     * @return List返回对应产品的所有利率信息
     */
    public List<Interest> findByProId(final String iProId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("iProId", iProId);
        return super.select("selectByProperties", map);
    }
    /**
     * 增加多个利率信息.
     * @param list Interest集合
     * @return boolean插入成功返回true,失败false
     */
    public boolean insertInterest(List<Interest> list) {
        return super.insertObj("insertInterests",list);
    }

    /**
     * 根据产品ID删除利率信息
     * @param proId 产品ID
     * @return  true:删除成功,false:删除失败
     */
    public boolean deleteInterestByProId(String iProId){
        return super.deletes("deleteInterestByProId",iProId);
    }
}
