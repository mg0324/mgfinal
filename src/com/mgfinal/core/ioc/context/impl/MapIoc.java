package com.mgfinal.core.ioc.context.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.mgfinal.core.ioc.context.inter.Ioc;
/**
 * map类型的容器工厂
 * @author mg
 * @date 2016-5-14
 */
public class MapIoc implements Ioc{
	
	private Map<String,Object> __ioc;
	
	public MapIoc(){
		__ioc = new HashMap<String,Object>();
	}
	
	public void set(String key,Object value){
		synchronized (key) {
			__ioc.put(key, value);
		}
	}
	
	public Object get(String key){
		synchronized (key) {
			return __ioc.get(key);
		}
	}

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		json.put("size", __ioc.size());
		json.put("ioc", __ioc.keySet());
		return json.toJSONString();
	}

	@Override
	public void destory() {
		__ioc.clear();
	}
	
}
