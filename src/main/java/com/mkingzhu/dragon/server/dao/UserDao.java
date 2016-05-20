package com.mkingzhu.dragon.server.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.mkingzhu.dragon.server.dao.dataobject.UserDo;

public interface UserDao {
    public void save(UserDo userDo)
            throws DataAccessException;

    public UserDo get(@Param("openId") String openId,
                      @Param("time") Date time)
            throws DataAccessException;
}
