package com.echo.user.web;

import com.alibaba.fastjson.JSON;
import com.echo.commons.exception.BaseException;
import com.echo.commons.web.MyBaseController;
import com.echo.mybator.entity.UUser;
import com.echo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends MyBaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserInfo.do", method = RequestMethod.GET)
    public String getUserInfo(String userName) throws Exception {
        try {
            UUser uUser = userService.getUserInfoByUserName(userName);
        } catch (BaseException e) {
            e.printStackTrace();
        }

        return "index";
    }
}
