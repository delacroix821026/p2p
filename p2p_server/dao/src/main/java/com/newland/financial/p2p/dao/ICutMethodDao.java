package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.CutMethod;

import java.util.List;
/**
 * @author Mxia
 */
public interface ICutMethodDao {
    /**
     * 根据产品编码删除对应产品扣款方式.
     * @param proId 产品编码
     * @return true or false
     */
    Boolean deleteCutMethod(String proId);
    /**
     * 插入扣款方式.
     * @param cutMethod 实体
     * @return true or false
     */
    Boolean insertCutMethod(CutMethod cutMethod);
    /**
     * 根据产品编码查询相应的扣款方式.
     * @param proId 产品编码
     * @return List CutMethod集合
     */
    List<CutMethod> selectCutMethod(String proId);
}
