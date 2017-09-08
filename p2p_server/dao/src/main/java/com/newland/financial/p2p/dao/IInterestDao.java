package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Interest;

import java.util.List;

/**
 * 定义对利率进行操作的接口.
 * @author Mxia
 */
public interface IInterestDao {
    /**
     * 增加利率信息.
     * @param interest Interest对象
     * @return boolean插入成功返回true,失败false
     */
    boolean insertInterest(Interest interest);

    /**
     * 删除利率信息.
     * @param ittId String利率编号
     * @return boolean删除成功返回true,失败false
     */
    boolean deleteInterest(String ittId);

    /**
     * 修改利率信息.
     * @param interest Interest对象
     * @return boolean修改成功返回true,失败false
     */
    boolean updateInterest(Interest interest);

    /**
     * 查看某产品的所有利率信息.
     * @param proId String产品编号
     * @return List返回对应产品的所有利率信息
     */
    List<Interest> findByProId(String proId);
}
