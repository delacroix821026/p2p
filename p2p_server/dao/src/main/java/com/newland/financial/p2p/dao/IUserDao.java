package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.User;
import java.util.List;

/**
 *
 * */
public interface IUserDao {
    /**
     *@param user User
     * */
    void insert(User user);
    /**
     *@param id String
     * @return User
     * */
    User findById(String id);
    /**
     * @return List
     * */
    List<User> findAll();
}
