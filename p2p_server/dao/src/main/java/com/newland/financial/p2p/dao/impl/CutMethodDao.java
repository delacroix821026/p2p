package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.ICutMethodDao;
import com.newland.financial.p2p.domain.entity.CutMethod;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Mxia
 */
@Repository
public class CutMethodDao extends
        MybatisBaseDao<CutMethod> implements ICutMethodDao {
    /**
     * 根据产品编码删除对应产品扣款方式.
     * @param proId 产品编码
     * @return true or false
     */
    public Boolean deleteCutMethod(String proId) {
        Map map = new HashMap();
        map.put("proId",proId);
        return super.deletes("deleteByProperty",map);
    }

    /**
     * 插入扣款方式.
     * @param cutMethod 实体
     * @return true or false
     */
    public Boolean insertCutMethod(CutMethod cutMethod) {
        return super.insertSelective(cutMethod);
    }

    /**
     * 根据产品编码查询相应的扣款方式.
     * @param proId 产品编码
     * @return List CutMethod集合
     */
    public List<CutMethod> selectCutMethod(String proId) {
        Map map = new HashMap();
        map.put("proId",proId);
        return super.select("selectByProperty",map);
    }
}
