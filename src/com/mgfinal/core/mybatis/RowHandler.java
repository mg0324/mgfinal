package com.mgfinal.core.mybatis;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
/**
 * 自定义row的handler，主要是获取查询到的行数
 * @author mg
 * @date 2016-5-3
 *
 */
public class RowHandler implements ResultHandler{
	
	private String val;
	
	@Override
	public void handleResult(ResultContext context) {
		val = context.getResultObject().toString();
	}
	
	public String getValue(){
		return val;
	}

}
