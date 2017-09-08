package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.Lender;
/**
 *定义对Lender进行操作的service接口.
 * @author cendaijuan
 * */
public interface ILenderService {
    /**
     * 获取用户信息.
     * @param userId String用户编号
     * @return Lender返回用户信息.
     * */
    Lender getLender(String userId);
    /**
     * 清楚用户数据,目前禁止使用.
     *@param userId String用户编号
     * */
    void clear(String userId);
}
