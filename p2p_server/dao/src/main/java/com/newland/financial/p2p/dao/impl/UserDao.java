package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IUserDao;
import com.newland.financial.p2p.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * test.
 * */
@Repository
public class UserDao extends MybatisBaseDao<User> implements IUserDao {
    /**
     * @param user User
     * */
    public void insert(final User user) {
        super.insertSelective(user);
    }
    /**
     * @param id String
     * @return User
     * */
    public User findById(final String id) {
        return super.selectByPrimaryKey(id);
    }
    /**
     * @return List
     * */
    public List<User> findAll() {
        return super.selectAll();
    }
}

