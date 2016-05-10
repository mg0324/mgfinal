package com.mgfinal.core.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * mgfinal 封裝的mybatis的basedao类
 * @author mg
 * @date 2016-5-3
 *
 */
public abstract class BaseDao {
	protected Logger log = Logger.getLogger(BaseDao.class);
	/**
	 * mybatis的sqlSession工厂
	 */
	protected static SqlSessionFactory sqlSessionFactory;
	
	protected static SqlSession __session;
	protected static List<String> __list;
	
	static{
		String resource = "mybatis.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);
			__list = new ArrayList<String>();
		} catch (IOException e) {
			System.out.println("未找到classpath:/mybatis.xml文件！");
		}
	}
		
	
}
