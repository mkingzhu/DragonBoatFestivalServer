package com.mkingzhu.dragon.server.biz.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.mkingzhu.dragon.server.dao.UserDao;
import com.mkingzhu.dragon.server.dao.dataobject.UserDo;

public class UserManager {
    @Autowired
    private UserDao userDao;

    public void save(UserDo userDo)
            throws DataAccessException {
        userDao.save(userDo);
    }

    public UserDo get(String openId, Date time)
            throws DataAccessException {
        return userDao.get(openId, time);
    }
}
