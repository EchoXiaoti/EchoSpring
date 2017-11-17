package com.frist.controller;

import com.echo.commons.utils.BeanMapUtil;
import com.echo.mybator.entity.UUser;
import com.echo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class MyFristController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @RequestMapping("/index.do")
    public ModelAndView testViwer(String path){
        ModelAndView modelAndView = new ModelAndView("view");
        return  modelAndView;
    }

    @RequestMapping("/beantomap.do")
    public String beanTomap(String name) throws Exception {
        UUser uUser = userService.getUserInfoByUserName(name);

        Map<String, String> map = BeanMapUtil.bean2Map(uUser);

        logger.info("the map is {}", map);

        return map.toString();
    }
}
