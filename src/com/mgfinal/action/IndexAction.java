package com.mgfinal.action;

import javax.servlet.annotation.WebServlet;

import mg.mvc.core.MGWorkServlet;
@WebServlet("/index.do/*")
public class IndexAction extends MGWorkServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String index(){
		
		return "index";
	}

}
