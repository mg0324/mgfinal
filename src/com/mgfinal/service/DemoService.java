package com.mgfinal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.github.pagehelper.PageInfo;
import com.mgfinal.core.mybatis.MyBatisDDLException;
import com.mgfinal.dao.DemoDao;
import com.mgfinal.vo.Demo;

import mg.ioc.annotation.ToBean;
import mg.ioc.annotation.UseBean;
@ToBean
public class DemoService {
	@UseBean
	private DemoDao demoDao;
	
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

	public PageInfo<Demo> selectPage(HttpServletRequest request) {
		return this.demoDao.selectPage("com.mgfinal.vo.Demo.showAll", null, request);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMap() {
		return  (List<Map<String, Object>>) this.demoDao.executeQuery("com.mgfinal.vo.Demo.findMap", null);
	}

	public void addDemo() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("pwd", Math.random());
		p.put("username", UUID.randomUUID().toString().substring(0, 10));
		this.demoDao.executeUpdate("com.mgfinal.vo.Demo.addDemo", p);
	}

	public void add2Demo() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("pwd", Math.random());
		p.put("username", UUID.randomUUID().toString().substring(0, 10));
		SqlSession session = null;
		try {
			//操作1
			session = this.demoDao.executeUpdateWithTx("com.mgfinal.vo.Demo.addDemo", p, session);
			//操作2
			session = this.demoDao.executeUpdateWithTx("com.mgfinal.vo.Demo.addDemo", null, session);
			//提交事务
			session.commit();
		} catch (MyBatisDDLException e) {
			session.rollback();//回滚
			e.printStackTrace();
		}finally{
			if(session!=null) session.close();
		}
		
	}
}
