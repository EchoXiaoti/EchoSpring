package com.echo.user.service.impl;

import com.alibaba.fastjson.JSON;

import com.echo.mybator.entity.UUser;
import com.echo.mybator.mapper.UUserMapper;
import com.echo.user.dao.UserDao;
import com.echo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userSerivce")
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UUserMapper userDao;

    @Autowired
    private UserDao uDao;

    @Override
    public UUser getUserInfo() {
        UUser user = userDao.selectByPrimaryKey("testuuidddddddddd");
        logger.info("user is {}", JSON.toJSONString(user));
        System.out.print(JSON.toJSONString(user));
        return user;
    }

    @Override
    public UUser getUserInfoByUserName(String userName) throws Exception {
        return uDao.getUserInfoByUserName(userName);
    }
}
