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
    public final boolean insertInterest(final Interest interest) {
        return super.insertSelective(interest);
    }
    /**
     * 删除利率信息.
     * @param ittId String利率编号
     * @return boolean删除成功返回true,失败false
     */
    public final boolean deleteInterest(final String ittId) {
        return super.deleteByPrimaryKey(ittId);
    }

    /**
     * 修改利率信息.
     * @param interest Interest对象
     * @return boolean修改成功返回true,失败false
     */
    public final boolean updateInterest(final Interest interest) {
        return super.updateByPrimaryKeySelective(interest);
    }

    /**
     * 查看某产品的所有利率信息.
     * @param iProId String产品编号
     * @return List返回对应产品的所有利率信息
     */
    public final List<Interest> findByProId(final String iProId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("iProId", iProId);
        return super.select("selectByProperties", map);
    }


}
