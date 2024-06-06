package com.pjt.testdemo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.testdemo.model.dao.MenuDao;
import com.pjt.testdemo.model.dto.Menu;

@Service
public class MenuServiceImpl implements MenuService {
	private final MenuDao menuDao;

	@Autowired
	public MenuServiceImpl(MenuDao dao) {
		menuDao = dao;
	}
	
	@Override
	public void addMenus(Menu menu) {
		menuDao.addMenus(menu);
	}

	@Override
	public List<Menu> weeklyMenu() {
		return menuDao.weeklyMenu();
	}

	@Override
	public int setSoldout(int id, int status) {
		HashMap<String, Integer> params = new HashMap<>();
		params.put("id", id);
		params.put("status", status);
		return menuDao.setSoldout(params);
	}

	@Override
	public List<Integer> getSoldout() {
		return menuDao.getSoldout();
	}
	
	@Override
	public List<Menu> todayMenu() {
		return menuDao.todayMenu();
	}
	
	//TEST
	@Override
	public int test() {
		return menuDao.test();
	}

	@Override
	public List<Menu> getBoard(int id) {
		return menuDao.getBoard(id);
	}

}
