package com.mgfinal.core.mybatis;

import java.io.IOException;
import java.io.InputStream;

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
	/**
	 * mybatis的sqlSession工厂
	 */
	protected static SqlSessionFactory sqlSessionFactory;
	protected static Logger log = Logger.getLogger(BaseDao.class);
	protected static SqlSession __session;
	
	static{
		String resource = "mybatis.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);
		} catch (IOException e) {
			log.info("未找到classpath:/mybatis.xml文件！");
		}
	}
		
	
}
