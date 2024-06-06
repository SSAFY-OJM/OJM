package com.pjt.testdemo.service;

import java.util.List;

import com.pjt.testdemo.model.dto.Menu;
import com.pjt.testdemo.model.dto.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	public void joinUser(User user);
	public void joinAdmin(User user);
	public boolean valID(String id);
	public User login(User.LoginSet set, HttpServletRequest httpServletRequest);
	public void logout(HttpServletRequest request);
}
