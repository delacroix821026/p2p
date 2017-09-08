package com.newland.financial.p2p.service.Impl;


import com.newland.financial.p2p.dao.ITCmmCityDao;
import com.newland.financial.p2p.dao.IUserDao;
import com.newland.financial.p2p.dao.IUserDao1;
import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;
import com.newland.financial.p2p.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * test.
 * */
@Service
public class UserServiceImpl implements IUserService {
    /***/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /***/
    @Autowired
    private IUserDao userDao;
    /***/
    @Autowired
    private IUserDao1 userDao1;
    /***/
    @Autowired
    private ITCmmCityDao tCmmCityDao;
    /**
     *@param user User
     * */
    public void addUser(final User user) {
        logger.info("service--添加的user----" + user.toString());
        userDao.insert(user);
    }
    /**
     * test.
     * @param userId String
     * @return User
     * */
    public User getUserById(final String userId) {
        logger.info("service--findById(userId)-------:"
                + userDao.findById(userId));
        return userDao.findById(userId);
    }
    /**
     * tset.
     * */
   public void addUsersForTest() {

//        User user = new User();
//        user.setId("1111111111111111111111111111111111111111111");
//        user.setName("yuan");
//        userDao.insert(user);
//
//        User_1 user_1 = new User_1();
//        user_1.setId("2222");
//        user_1.setName("qing");
//        userDao_1.insert(user_1);
    }
    /**
     *@param provNm String
     * @return List
     * */
    public List<TCmmCity> findCityByName(final String provNm) {
        return tCmmCityDao.findByProvNm(provNm);
    }
    /**
     * @return List
     * */
    public List<User> findAll() {
            return userDao.findAll();
    }



}
