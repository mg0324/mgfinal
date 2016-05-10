package com.mgfinal.core.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgfinal.core.mybatis.util.SqlHelper;

/**
 * 实现basedao的实现类，提供常用的dao层方法
 * @author mg
 * @date 2016-5-3
 *
 */
public class BaseDaoImpl<T> extends BaseDao{
	protected SqlSession sqlSession;
	/**
	 * 获取sqlSession，不带事务
	 * @return
	 */
	public SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}
	/**
	 * 获取sqlSession,带事务，不自动提交的会话
	 * @return
	 */
	public SqlSession getSqlSession(Boolean b){
		return sqlSessionFactory.openSession(ExecutorType.BATCH,b);
	}
	/**
	 * 查询得到某个对象
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 查询得到某个对象
	 */
	public T selectOne(String id,Object p) {
		sqlSession = getSqlSession();
		T obj = sqlSession.selectOne(id, p);
		sqlSession.close();
		return obj;
	}
	/**
	 * 查询得到对象集合
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 查询得到对象集合
	 */
	public List<T> selectList(String id,Object p){
		sqlSession = getSqlSession();
		List<T> list = sqlSession.selectList(id, p);
		sqlSession.close();
		return list;
	}
	/**
	 * 查询得到list<map>
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 查询得到list<map>
	 */
	public List<Map<String,Object>> selectListMap(String id,Object p){
		sqlSession = getSqlSession();
		List<Map<String,Object>> list = sqlSession.selectList(id,p);
		sqlSession.close();
		return list;
	}
	/**
	 * 查询得到map
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 查询得到map
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectMap(String id,Object p){
		sqlSession = getSqlSession();
		Map<String,Object> map = sqlSession.selectMap(id,p,uuid());
		sqlSession.close();
		return (Map<String, Object>) map.get(null);
	}
	/**
	 * 查询得到某列的数字，一般用于查询count
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 一行一列的数字值
	 */
	public Integer selectForInt(String id,Object p){
		sqlSession = getSqlSession();
		RowHandler rh = new RowHandler();
		sqlSession.select(id, p, rh);
		sqlSession.close();
		return Integer.parseInt(rh.getValue());
	}
	/**
	 * 查询某一列的值
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 查询得到的一行一列的值
	 */
	public String selectForString(String id,Object p){
		sqlSession = getSqlSession();
		StringHandler sh = new StringHandler();
		sqlSession.select(id, p, sh);
		sqlSession.close();
		return sh.getValue();
	}
	/**
	 * 基于mybatis pagehelper工具的分页封装查询
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @param req 请求
	 * @return PageInfo分页对象
	 */
	public PageInfo<T> selectPage(String id,Object p,HttpServletRequest req){
		PageHelper.startPage(req);
		List<T> list = this.selectList(id, p);
		return new PageInfo<T>(list);
	}
	/**
	 * 获取不带-的uuid32位
	 * @return
	 */
	private String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**新增save,update,delete方法**/
	
	
	/**新增通用查询，和执行方法**/
	/**
	 * 通用Query方法
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 结果集
	 */
	public Object executeQuery(String id,Object p){
		sqlSession = getSqlSession();
		ResultSetHandler rsh = new ResultSetHandler();
		sqlSession.select(id,p,rsh);
		sqlSession.close();
		return rsh.getValue();
	}
	/**
	 * 通用DDL方法
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 影响函数
	 */
	public int executeUpdate(String id,Object p){
		sqlSession = getSqlSession();
		int row = sqlSession.update(id, p);
		return row;
	}
	/**
	 * 通用DDL方法,带有事务，需要session传递
	 * @param id 带namespace的sql的id
	 * @param p sql的参数
	 * @return 影响函数
	 * @throws MyBatisDDLException 
	 */
	public void executeUpdateWithTx(String id,Object p){
		if(__session == null){
			//新事物
			__session = getSqlSession();
			__list.clear();
		}
		String sql = SqlHelper.getNamespaceSql(__session, id, p);
		__list.add(sql);
	}
	/**
	 * 提交
	 */
	public void commit(){
		if(__session == null){
			System.out.println("未开启事务！");
			return ;
		}
		Connection conn = __session.getConnection();
		try {
			conn.setAutoCommit(false);
			for(String sql : __list){
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				log.info("sqlHelper:"+sql);
			}
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				log.debug("回滚成功");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
}
