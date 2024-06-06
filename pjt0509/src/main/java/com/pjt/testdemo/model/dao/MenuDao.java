package com.pjt.testdemo.model.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.pjt.testdemo.model.dto.Menu;

public interface MenuDao {
	public void addMenus(Menu menu);
	public List<Menu> weeklyMenu();
	public int setSoldout(HashMap<String, Integer> params);
	public List<Integer> getSoldout();
	public List<Menu> todayMenu();
	public List<Menu> getBoard(int id);
	public int test();
}
