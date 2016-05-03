package com.mgfinal.core.mybatis;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;

/**
 * 实现basedao的实现类，提供常用的dao层方法
 * @author mg
 * @date 2016-5-3
 *
 */
public class BaseDaoImpl<T> extends BaseDao{
	/**
	 * 获取sqlSession
	 * @return
	 */
	private SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}
	
	public T selectOne(String id,Object p) {
		SqlSession session = getSqlSession();
		T obj = session.selectOne(id, p);
		session.close();
		return obj;
	}
	
	public List<T> selectList(String id,Object p){
		SqlSession session = getSqlSession();
		List<T> list = session.selectList(id, p);
		session.close();
		return list;
	}
	
	public List<Map<String,Object>> selectListMap(String id,Object p){
		SqlSession session = getSqlSession();
		List<Map<String,Object>> list = session.selectList(id,p);
		session.close();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectMap(String id,Object p){
		SqlSession session = getSqlSession();
		Map<String,Object> map = session.selectMap(id,p,uuid());
		session.close();
		return (Map<String, Object>) map.get(null);
	}
	
	public Integer selectForInt(String id,Object p){
		SqlSession session = getSqlSession();
		RowHandler rh = new RowHandler();
		session.select(id, p, rh);
		session.close();
		return Integer.parseInt(rh.getValue());
	}
	
	public String selectForString(String id,Object p){
		SqlSession session = getSqlSession();
		StringHandler sh = new StringHandler();
		session.select(id, p, sh);
		session.close();
		return sh.getValue();
	}
	
	private String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	
}
