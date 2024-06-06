package com.pjt.testdemo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.testdemo.model.dao.UserDao;
import com.pjt.testdemo.model.dto.User;
import com.pjt.testdemo.model.dto.User.LoginSet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	private final UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao dao) {
		userDao = dao;
	}
	
	@Override
	public void joinUser(User u) {
		userDao.joinUser(u);
	}
	
	@Override
	public void joinAdmin(User u) {
		userDao.joinAdmin(u);
	}

	@Override
	public boolean valID(String id) {
		int result = userDao.valID(id);
		if(result >= 1) return false;
		return true;
	}

	@Override
	public User login(LoginSet set, HttpServletRequest httpServletRequest) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println(set.getId());
		System.out.println(set.getPassword());
		map.put("id", set.getId());
		map.put("password", set.getPassword());
		if(userDao.login(map) != 1) { // login failure
			System.out.println("LOGIN FAIL");
			// return false;
			return null;
		}else { // login success
			User user = userDao.getInfo(set.getId());
			System.out.println(user); 
			HttpSession session = httpServletRequest.getSession(true);
			session.setAttribute("user", user);
			
			return user;
//			return true;
		}
		
	}
	// front 로그인 테스트 해주세용 ~ ~ ~ ~ 
	/*	1. vue session package 설치
	 * 	
	 */
	@Override
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate(); 
	}

	

}
