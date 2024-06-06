package com.pjt.testdemo.service;

import java.util.List;

import com.pjt.testdemo.model.dto.Menu;

public interface MenuService {
	void addMenus(Menu menu);
	List<Menu> weeklyMenu();
	int setSoldout(int id, int status);
	List<Integer> getSoldout();
	List<Menu> todayMenu();
	
	List<Menu> getBoard(int id);
	// ======== TEST
	int test();
}
