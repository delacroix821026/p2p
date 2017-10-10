package com.newland.financial.p2p.service;


import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;

import java.util.List;
/**
 * @author cengdaijuan
 * */
public interface IUserService {
    /**
     * @param user User
     * */
    void addUser(User user);
    /**
     *@param userId String
     *@return User
     * */
    User getUserById(String userId);
    /***/
    void addUsersForTest();
    /**
     * @param provNm String
     *@return List
     * */
    List<TCmmCity> findCityByName(String provNm);
    /**
     *@return List
     * */
    List<User> findAll();
}
