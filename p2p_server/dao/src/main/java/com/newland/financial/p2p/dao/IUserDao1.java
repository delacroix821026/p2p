package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.User1;
/**
 *tset.
 * */
public interface IUserDao1 {
    /**
     *@param user User_1
     * */
    void insert(User1 user);
    /**
     *@param id String
     * @return User_1
     * */
    User1 findById(String id);
}
