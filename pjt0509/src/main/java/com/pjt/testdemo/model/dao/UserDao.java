package com.pjt.testdemo.model.dao;

import java.util.HashMap;

import com.pjt.testdemo.model.dto.User;

public interface UserDao {
	public void joinUser(User user);
	public void joinAdmin(User user);
	public int valID(String id);
	
	public int login(HashMap<String, String> map);	
	public User getInfo(String id);
	
}
