package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.domain.entity.Lender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 对用户进行操作的Dao层类.
 * @author Mxia
 */
@Repository
public class LenderDao extends
        MybatisBaseDao<Lender> implements ILenderDao {
    /**
     * 日志对象.
     * */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 增加用户信息.
     *
     * @param lender Lender用户对象
     * @return boolean插入成功返回true,失败false
     */
    public boolean insertLender(final Lender lender) {
        return super.insertSelective(lender);
    }

    /**
     * 删除用户信息.
     *
     * @param userId String用户编号
     * @return boolean删除成功返回true,失败false
     */
    public boolean deleteLender(final String userId) {
        return super.deleteByPrimaryKey(userId);
    }

    /**
     * 修改用户信息.
     *
     * @param lender Lender用户对象
     * @return boolean修改成功返回true,失败false
     */
    public boolean updateLender(final Lender lender) {
        return super.updateByPrimaryKeySelective(lender);
    }

    /**
     * 查看用户信息.
     *
     * @param userId String用户编号
     * @return Lender根据用户编号,返回用户实体
     */
    public Lender findById(final String userId) {
        logger.info("LenderDao.userId--:" + userId);
        logger.info("查询结果:" + super.selectByPrimaryKey(userId).toString());
        return super.selectByPrimaryKey(userId);
    }

    /**
     * 测试恢复专用.
     *
     * @param userId String用户编号
     * @return boolean恢复成功返回true,失败false
     */
    public boolean getBack(final String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return super.update("getBackData", map);
    }
}
