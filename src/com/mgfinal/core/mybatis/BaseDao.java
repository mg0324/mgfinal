package com.mgfinal.core.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mgfinal.log.MgLog;

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
	protected static SqlSession __session;
	
	static{
		String resource = "mybatis.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);
			MgLog.log.info("mgfinal mybatis初始化成功！");
		} catch (IOException e) {
			MgLog.log.info("未找到classpath:/mybatis.xml文件！");
		}
	}
		
	
}
