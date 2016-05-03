package com.mgfinal.action;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.mgfinal.service.DemoService;
import com.mgfinal.vo.Demo;

import mg.core.MGWorkServlet;
@WebServlet("/test.do/*")
public class TestAction extends MGWorkServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static DemoService demoService = new DemoService();
	public String selectOne(){
		Demo demo = demoService.show();
		this.setAttr("demo", demo);
		return "index";
	}
	
	public String selectList(){
		List<Demo> dList = demoService.showAll();
		this.setAttr("dList", dList);
		return "index";
	}
	
	public String selectMap(){
		Map<String,Object> map = demoService.showMapById("2");
		this.setAttr("map", map);
		return "index";
	}
	
	public String selectListMap(){
		List<Map<String,Object>> mapList = demoService.showListMap();
		this.setAttr("mapList", mapList);
		return "index";
	}
	
	public String selectCount(){
		Integer size = demoService.showSize();
		this.setAttr("size", size);
		return "index";
	}
	
	public String selectString(){
		String name = demoService.showString("33");
		this.setAttr("name", name);
		return "index";
	}

}
