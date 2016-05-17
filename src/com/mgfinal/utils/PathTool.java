package com.mgfinal.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 路径工具集
 * @author mg
 * @date 2016-5-15
 */
public class PathTool {
	/**
	 * 得到classpath的根路径
	 * @return
	 */
	public static String getClassPath(){
		return PathTool.class.getResource("/").toString().replace("file:/", "");
	}
	/**
	 * 得到web项目的服务器根目录
	 * @param request
	 * @return
	 */
	public static String getWebRootPath(HttpServletRequest request){
		return request.getServletContext().getRealPath("/").replace("\\", "/");
	}
}
