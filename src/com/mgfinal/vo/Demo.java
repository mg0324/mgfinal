package com.mgfinal.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.ext_ext.mybatisext.annotation.TableName;

@TableName(name="mg_user")
public class Demo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String pwd;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
