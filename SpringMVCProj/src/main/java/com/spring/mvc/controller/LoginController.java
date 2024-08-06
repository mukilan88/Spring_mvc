package com.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.model.LoginAction;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public ModelAndView authenticate(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String result = LoginAction.verifyUser(username, password);
		if ("success".equals(result))
			return new ModelAndView("success.jsp", "username", username);
		else
			return new ModelAndView("failure.jsp");
	}
}
