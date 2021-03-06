package com.mgfinal.core.mvc.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.mgfinal.core.mvc.core.file.UFile;
/**
 * 抽象最高层servlet
 * @author mg
 * @date 2016-5-15
 *
 */
public class BaseWorkServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 网页文件默认前缀/web-inf/pages
	 */
	protected String MGFINAL_WEBFLOADER_PREFIX = "/WEB-INF/pages";
	/**
	 * 网页文件后缀，默认.html
	 */
	protected String MGFINAL_WEB_PAGE_STUFFIX = ".html";
	/**
	 * 默认视图，jsp
	 */
	protected String MGFINAL_WEB_VIEW_TYPE = "jsp";
	/**
	 * 上传文件大小，默认2M
	 */
	protected int MGFINAL_ALLOW_FILE_SIZE = 2;
	/**
	 * 总文件大小，默认10M
	 */
	protected int MGFINAL_ALLOW_TOTAL_FILE_SIZE = 10;
	/**
	 * 允许上传的文件了类型，默认无限制
	 */
	protected String MGFINAL_ALLOW_FILE_TYPE = "*";
	/**
	 * 普通表单信息
	 */
	protected Map<String,String> __form_field_map = new HashMap<String,String>();
	/**
	 * 文件表单信息
	 */
	protected Map<String,UFile> __form_file_map = new HashMap<String,UFile>();
}
