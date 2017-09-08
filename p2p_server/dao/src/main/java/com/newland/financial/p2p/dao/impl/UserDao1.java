package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IUserDao1;
import com.newland.financial.p2p.domain.entity.User1;
import org.springframework.stereotype.Repository;
/**
 * test.
 * */
@Repository
public class UserDao1 extends MybatisBaseDao<User1> implements IUserDao1 {
    /**
     *@param user User_1
     * */
    public void insert(final User1 user) {
        super.insertSelective(user);
    }
    /**
     *@param id String
     *@return User_1
     * */
    public User1 findById(final String id) {
        return super.selectByPrimaryKey(id);
    }
}
