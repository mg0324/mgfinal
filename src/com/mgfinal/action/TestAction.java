package com.mgfinal.action;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.alibaba.fastjson.JSONObject;
import com.ext_ext.mybatisext.helper.Page;
import com.github.pagehelper.PageInfo;
import com.mgfinal.service.DemoService;
import com.mgfinal.vo.Demo;

import mg.ioc.annotation.UseBean;
import mg.mvc.core.MGWorkServlet;
@WebServlet("/test.do/*")
public class TestAction extends MGWorkServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@UseBean
	private DemoService demoService;

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
		String name = demoService.showString("2116");
		this.setAttr("name", name);
		return "index";
	}
	
	public String toPage(){
		PageInfo<Demo> page = demoService.selectPage(request);
		this.setAttr("page", page);
		System.out.println(JSONObject.toJSONString(page));
		return "page";
	}
	
	public String executeQuery(){
		List<Map<String,Object>> list = demoService.findMap();
		this.setAttr("findList", list);
		return "index";
	}
	
	public String executeUpdate(){
		demoService.addDemo();
		this.setAttr("msg", "添加成功！");
		return "index";
	}
	
	public String executeUpdateTx(){
		demoService.add2Demo();
		this.setAttr("msg2", "操作成功，事务提交了！");
		return "index";
	}
	
	public String testSave(){
		demoService.save();
		this.setAttr("msg3", "保存成功");
		return "index";
	}
	
	public String testSaveList(){
		demoService.saveList();
		this.setAttr("msg4", "List保存成功");
		return "index";
	}
	
	public String testDelete(){
		demoService.delete();
		this.setAttr("msg5", "删除成功");
		return "index";
	}
	
	public String testUpdate(){
		demoService.update();
		this.setAttr("msg6", "更新成功");
		return "index";
	}
	
	public String testCount(){
		int count = demoService.count();
		this.setAttr("count_d", count);
		return "index";
	}
	
	public String testSelectList(){
		List<Demo> list = demoService.selectList();
		this.setAttr("dlist", list);
		return "index";
	}
	
	public String testPage(){
		Demo d = new Demo();
		d.setUsername("cc");
		List<Demo> listd = this.demoService.page(1,10, d);
		this.setAttr("listd", listd);
		return "index";
	}
	
	
	

}
