package com.mgfinal.service;

import java.util.List;
import java.util.Map;

import com.mgfinal.dao.DemoDao;
import com.mgfinal.vo.Demo;

public class DemoService {
	private DemoDao demoDao;
	
	public DemoService(){
		demoDao = new DemoDao();
	}
	
	public Demo show(){
		String id = "1";
		return (Demo) this.demoDao.selectOne("com.mgfinal.vo.Demo.showId",id);
	}
	
	public List<Demo> showAll(){
		return this.demoDao.selectList("com.mgfinal.vo.Demo.showAll",null);
	}

	public Map<String, Object> showMapById(String id) {
		return this.demoDao.selectMap("com.mgfinal.vo.Demo.showMapById", id);
	}

	public List<Map<String, Object>> showListMap() {
		return this.demoDao.selectListMap("com.mgfinal.vo.Demo.showListMap", null);
	}

	public Integer showSize() {
		return this.demoDao.selectForInt("com.mgfinal.vo.Demo.showSize", null);
	}

	public String showString(String id) {
		return this.demoDao.selectForString("com.mgfinal.vo.Demo.showString", id);
	}
}
