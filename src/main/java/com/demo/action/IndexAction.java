package com.demo.action;

import javax.servlet.annotation.WebServlet;

import com.mgfinal.core.mvc.core.BaseAction;
@WebServlet("/index.do/*")
public class IndexAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String index(){
		
		return "index";
	}

}
