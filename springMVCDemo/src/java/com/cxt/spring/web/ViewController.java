package com.cxt.spring.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest request){
		String path = request.getParameter("path") + "";
		ModelAndView mv = new ModelAndView();
		mv.setViewName(path);
		return mv;
	}
}
