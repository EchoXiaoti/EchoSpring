package com.echo.user.dao.impl;

import com.echo.commons.dao.BaseDaoSupport;
import com.echo.mybator.entity.UUser;
import com.echo.user.dao.UserDao;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义用户Dao实现类
 *
 * @author Echo-cxt
 * @create 2017-11-09 10:05 AM
 **/

@Component("uDao")
public class UserDaoImp extends BaseDaoSupport implements UserDao{
    @Override
    public UUser getUserInfoByCondition(Map<Object, String> param) {
        return null;
    }

    @Override
    public UUser getUserInfoByUserName(String userName) throws Exception {
        return (UUser)super.findForObject("user.getUserInfoByUserName", userName);
    }
}
