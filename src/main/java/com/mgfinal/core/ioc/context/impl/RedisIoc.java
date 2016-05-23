package com.mgfinal.core.ioc.context.impl;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.mgfinal.core.ioc.context.inter.Ioc;
/**
 * redis容器工厂实现
 * @author mg
 * @date 2016-5-14
 */
import com.mgfinal.core.ioc.util.SerializeUtil;

import redis.clients.jedis.Jedis;
public class RedisIoc implements Ioc{
	private Jedis __ioc;
	
	public RedisIoc(String host,int port){
		__ioc = new Jedis(host, port);
	}

	public void set(String key, Object value) {
		__ioc.set(key.getBytes(), SerializeUtil.serialize(value));
	}

	public Object get(String key) {
		synchronized (key) {
			Object obj = SerializeUtil.unserialize(__ioc.get(key.getBytes()));
			return obj;
		}
	}

	public String toJsonString() {
		JSONObject json = new JSONObject();
		Set<String> keys = __ioc.keys("*");
		json.put("size", keys.size());
		json.put("ioc", keys);
		return json.toJSONString();
	}

	public void destory() {
		__ioc.flushDB();
	}
}
