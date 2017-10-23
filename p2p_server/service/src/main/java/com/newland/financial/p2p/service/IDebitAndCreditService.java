package com.newland.financial.p2p.service;

import com.newland.financial.p2p.common.exception.OverloadException;
import com.newland.financial.p2p.domain.entity.*;

import java.math.BigDecimal;
import java.util.List;
/**
 * 定义对贷款信息操作的service接口.
 * @author cendaijuan
 * */
public interface IDebitAndCreditService {
    /**
     * 进行贷款.
     * @param userId String用户编号
     * @param productId String产品编号
     * @param money BigDecimal贷款金额
     * @param interestId String利率编号
     * @throws OverloadException 贷款金额超出额度限制
     */
    void createDebitAndCredit(String userId, String productId,
        BigDecimal money, int interestId) throws OverloadException;

    /**
     * 查询历史借款记录.
     * @param userId String用户编号
     * @return List返回该用户所有的贷款信息
     */
    List<DebitAndCredit> findDebitAndCreditHistory(String userId);

    /**
     * 插入用户.
     * @param lender Lender对象
     * @return  boolean成功返回true,失败false
     * */
    boolean insertLender(Lender lender);
    /**
     * 插入利率信息.
     * @param interest Interest对象
     * @return  boolean成功返回true,失败false
     * */
    boolean insertInterest(Interest interest);
    /**
     * 插入产品.
     * @param product Product对象
     * @return  boolean成功返回true,失败false
     * */
    boolean insertProduct(Product product);
    /**
     * 删除用户.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    boolean deleteLender(String userId);
    /**
     * 删除利率信息.
     * @param ittId String利率编号
     * @return  boolean成功返回true,失败false
     * */
    boolean deleteInterest(String ittId);
    /**
     * 删除产品.
     * @param proId String产品编号
     * @return  boolean成功返回true,失败false
     * */
    boolean deleteProduct(String proId);
    /**
     * 删除还款单.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    boolean deleteRepayALoan(String userId);
    /**
     * 删除贷款单.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    boolean deleteDebitAndCredit(String userId);
    /**
     *用户对应所有产品的贷款状态.
     * @param userId 用户编号
     * @return 返回所有产品贷款状态
     */
    Object findAllProStatus(String userId);
    /**
     *分页查询贷款单信息.
     * @param jsonStr 分页信息
     * @return 结果集
     */
    Object getDebitList(String jsonStr);
}
