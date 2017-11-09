package com.echo.user.service;

import com.echo.mybator.entity.UUser;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * 获取用户信息
     * @return
     */
    public UUser getUserInfo();

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public UUser getUserInfoByUserName(String userName) throws Exception;
}
