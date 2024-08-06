package com.spring.mvc.model;

public class LoginAction {
	public static String verifyUser(String username, String password) {
		if ("admin".equals(username) && "admin".equals(password))
			return "success";
		else
			return "failure";
	}
}
