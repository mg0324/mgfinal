package com.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.demo.dao.DemoDao;
import com.demo.vo.Demo;
import com.github.pagehelper.PageInfo;
import com.mgfinal.core.ioc.annotation.ToBean;
import com.mgfinal.core.ioc.annotation.UseBean;
import com.mgfinal.core.mybatis.service.BaseService;
@ToBean
public class DemoService extends BaseService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@UseBean
	private DemoDao demoDao;
	
	public Demo show(){
		String id = "1";
		return (Demo) this.demoDao.selectOne("com.demo.vo.Demo.showId",id);
	}
	
	public List<Demo> showAll(){
		return this.demoDao.selectList("com.demo.vo.Demo.showAll",null);
	}

	public Map<String, Object> showMapById(String id) {
		return this.demoDao.selectMap("com.demo.vo.Demo.showMapById", id);
	}

	public List<Map<String, Object>> showListMap() {
		return this.demoDao.selectListMap("com.demo.vo.Demo.showListMap", null);
	}

	public Integer showSize() {
		return this.demoDao.selectForInt("com.demo.vo.Demo.showSize", null);
	}

	public String showString(String id) {
		return this.demoDao.selectForString("com.demo.vo.Demo.showString", id);
	}

	public PageInfo<Demo> selectPage(HttpServletRequest request) {
		return this.demoDao.selectPage("com.demo.vo.Demo.showAll", null, request);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMap() {
		return  (List<Map<String, Object>>) this.demoDao.query("com.demo.vo.Demo.findMap", null);
	}

	public void addDemo() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("pwd", Math.random());
		p.put("username", UUID.randomUUID().toString().substring(0, 10));
		this.demoDao.ddl("com.demo.vo.Demo.addDemo", p);
	}

	public void add2Demo() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("pwd", new Date().toLocaleString());
		p.put("username", UUID.randomUUID().toString().substring(0, 10));
		//开启事务
		this.demoDao.start();
		//操作1
		this.demoDao.ddlTx("com.demo.vo.Demo.addDemo", p);
		//操作2
		this.demoDao.ddlTx("com.demo.vo.Demo.addDemo", p);
		//提交事务
		this.demoDao.end();
	}
	
	public void save(){
		Demo d = new Demo();
		d.setPwd("123456");
		d.setUsername("mybatis1234");
		this.demoDao.save(d, Demo.class);
	}
	
	public void saveList(){
		Demo d = new Demo();
		d.setPwd("123456");
		d.setUsername("mybatis1234");
		List<Demo> ld = new ArrayList<Demo>();
		ld.add(d);
		d = new Demo();
		d.setUsername("123213--");
		d.setPwd("987654321");
		ld.add(d);
		this.demoDao.saveList(ld, Demo.class);
	}
	
	public void delete(){
		Demo d = this.demoDao.one("id", 2107, Demo.class);
		this.demoDao.delete(d, Demo.class);
	}
	
	public List<Demo> selectList(){
		Demo d = new Demo();
		d.setUsername("123");
		return this.demoDao.list(d, Demo.class);
	}
	
	public void update(){
		Demo d = this.demoDao.one("id", 2109, Demo.class);
		d.setPwd("1234567890987654321");
		this.demoDao.update(d, Demo.class);
	}
	
	public List<Demo> page(int pageNo,int size,Demo condition) {
		return this.demoDao.page(Demo.class, pageNo,size, condition);
	}

	public int count() {
		return this.demoDao.count(null, Demo.class);
	}
	
}
