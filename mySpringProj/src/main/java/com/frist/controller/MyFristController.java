package com.frist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyFristController {

    @RequestMapping("/index.do")
    public ModelAndView testViwer(String path){
        ModelAndView modelAndView = new ModelAndView("view");
        return  modelAndView;
    }
}
