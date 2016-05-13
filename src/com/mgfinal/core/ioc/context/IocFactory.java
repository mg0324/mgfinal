package com.mgfinal.core.ioc.context;

import java.util.Properties;

import com.mg.log.MgLog;
import com.mg.util.PropTool;
import com.mgfinal.core.ioc.context.impl.MapIoc;
import com.mgfinal.core.ioc.context.impl.RedisIoc;
import com.mgfinal.core.ioc.context.inter.Ioc;

/**
 * ioc工厂 , 单例
 * @author mg
 * @date 2016-5-4
 */
public class IocFactory{
	/**
	 * 线程安全加锁,用来存放bean的容器
	 */
	private static Ioc factory;
	static{
		Properties prop = PropTool.use("mgfinal.properties");
		//首先判断mgwork.ioc.type map redis
		String iocType = prop.getProperty("mgfinal.ioc.type","map");
		if(iocType.equals("map")){
			//map容器
			factory = new MapIoc();
			MgLog.log.info("mgfinal ioc type is map");
		}else if(iocType.equals("redis")){
			//redis容器
			String host = prop.getProperty("mgfinal.ioc.redis.host","localhost");
			int port = Integer.parseInt(prop.getProperty("mgfinal.ioc.redis.port","6397"));
			factory = new RedisIoc(host, port);
			MgLog.log.info("mgfinal ioc type is redis");
		}
		
	}
	/**
	 * 加入factory容器中
	 * @param name 类名作为id
	 * @param newInstance 对象 
	 */
	public static void add(String name, Object newInstance) {
		synchronized (name) {
			factory.set(name, newInstance);
		}
	}
	/**
	 * 得到实例对象从容器中
	 * @param name id值
	 */
	public static Object get(String name){
		return factory.get(name);
	}
	/**
	 * 返回工厂的json字符串
	 * @return
	 */
	public static String toJsonString(){
		return factory.toJsonString();
	}
	/**
	 * 释放factory
	 */
	public static void destoryFactory() {
		factory.destory();
	}
	
}
