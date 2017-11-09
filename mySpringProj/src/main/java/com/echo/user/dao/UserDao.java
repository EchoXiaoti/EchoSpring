package com.echo.user.dao;

import com.echo.mybator.entity.UUser;

import java.util.Map;

public interface UserDao {

    /**
     * 按条件查询用户信息
     * @param param
     * @return
     */
    public UUser getUserInfoByCondition(Map<Object, String> param);

    /**
     *
     * @param userName
     * @return
     */
    public UUser getUserInfoByUserName(String userName) throws Exception;
}
