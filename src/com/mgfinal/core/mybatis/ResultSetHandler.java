package com.mgfinal.core.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
/**
 * 自定义结果集的handler，主要是通用返回
 * @author mg
 * @date 2016-5-3
 *
 */
public class ResultSetHandler implements ResultHandler{
	
	private List<Object> val = new ArrayList<Object>();
	
	@Override
	public void handleResult(ResultContext context) {
		Object obj = context.getResultObject();
		val.add(obj);
	}
	
	public List<Object> getValue(){
		return val;
	}

}
