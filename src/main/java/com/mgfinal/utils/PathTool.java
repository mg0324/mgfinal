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
	/**
	 * 得到上级路径
	 * @param path 路径
	 * @param c 回退几级，2的话，../../
	 * @return 返回回退路径，如果超出，就返回null
	 */
	public static String getParentPath(String path,int c){
		int a = path.split("/").length;
		if(c <= a){
			if(c == 0){
				return path.substring(0,path.lastIndexOf("/"));
			}else{
				path = path.substring(0,path.lastIndexOf("/"));
			}
			return getParentPath(path, --c);
		}else{
			return null;//超出
		}
	}
}
