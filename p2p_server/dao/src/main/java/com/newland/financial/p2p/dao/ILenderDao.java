package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Lender;

/**
 * 定义对lender操作的接口.
 * @author Mxia
 */
public interface ILenderDao {
    /**
     * 增加用户信息.
     *
     * @param lender Lender用户对象
     * @return boolean插入成功返回true,失败false
     */
    boolean insertLender(Lender lender);

    /**
     * 删除用户信息.
     *
     * @param userId String用户编号
     * @return boolean删除成功返回true,失败false
     */
    boolean deleteLender(String userId);

    /**
     * 修改用户信息.
     *
     * @param lender Lender用户对象
     * @return boolean修改成功返回true,失败false
     */
    boolean updateLender(Lender lender);

    /**
     * 查看用户信息.
     *
     * @param userId String用户编号
     * @return Lender根据用户编号,返回用户实体
     */
    Lender findById(String userId);

    /**
     * 测试恢复专用.
     *
     * @param userId String用户编号
     * @return boolean恢复成功返回true,失败false
     */
    boolean getBack(String userId);
}
