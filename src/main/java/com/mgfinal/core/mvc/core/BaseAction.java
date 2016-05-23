package com.mgfinal.core.mvc.core;

import java.io.File;

/**
 * @author 梅刚 2014-11-3 21:34
 * 
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.mgfinal.core.ioc.annotation.UseBean;
import com.mgfinal.core.ioc.context.IocFactory;
import com.mgfinal.core.mvc.core.file.UFile;
import com.mgfinal.log.MgLog;
import com.mgfinal.utils.PropTool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public abstract class BaseAction extends BaseWorkServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * request
	 */
	protected HttpServletRequest request;
	/**
	 * response
	 */
	protected HttpServletResponse response;
	/**
	 * 请求url
	 */
	public static String REQUEST_URL;
	
	
	/**
	 * freemarker配置
	 */
	private Configuration cfg;
	
	@Override
	public void init() throws ServletException {
		cfg = new Configuration();
		Properties prop = PropTool.use("mgfinal.properties");
		MGFINAL_WEB_PAGE_STUFFIX = prop.getProperty("mgfinal.webfolder.prefix","/WEB-INF/pages");
		MGFINAL_WEB_PAGE_STUFFIX = prop.getProperty("mgfinal.web.page.stuffix",".html");
		MGFINAL_WEB_VIEW_TYPE = prop.getProperty("mgfinal.web.view.type", "jsp");
		MGFINAL_ALLOW_FILE_SIZE = Integer.parseInt(prop.getProperty("mgfinal.allow.file.size","2"));
		MGFINAL_ALLOW_TOTAL_FILE_SIZE = Integer.parseInt(prop.getProperty("mgfinal.allow.file.total.size","10"));
		MGFINAL_ALLOW_FILE_TYPE = prop.getProperty("mgfinal.allow.file.type","*");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;
		//编码过滤
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		REQUEST_URL = request.getRequestURL().toString();
		MgLog.log.info("mgfinal work - " + REQUEST_URL);
		//设置根目录webroot
		this.setAttr("ROOT", request.getContextPath());
		runMethod();
	}
	/**
	 * 1.分析url得到要执行的方法名
	 * @param r
	 */
	public String getActionNameFromUrl(){
		if(request==null) return null;
		String url = request.getRequestURL().toString();
		return url.substring(url.lastIndexOf("/")+1,url.length());
	}
	/**
	 * 根据方法名，使用反射得到该方法对象
	 * @param r
	 * @return
	 */
	public Method getMethodByActionName(){
		Method m = null;
		try {
			m = this.getClass().getMethod(getActionNameFromUrl());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return m;
	}
	/**
	 * 解析UseBean注解
	 * @param className 类名
	 */
	@SuppressWarnings("rawtypes")
	private void injectUseBean(Object obj,String className) {
		try {
			Class clazz = Class.forName(className);
			//解析属性注解@UseBean
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				UseBean useBean = f.getAnnotation(UseBean.class);
				if(null != useBean){
					//System.out.println("mgworkAction:--className="+this.getClass().getName()+"的"+f.getName()+"属性 <-- 已被注入. ");
					//打开访问private的属性
					f.setAccessible(true);
					String use_class = f.getType().toString().split(" ")[1];//use注解
					Object use_obj = IocFactory.get(use_class);
					f.set(obj, use_obj);
					MgLog.log.info("mgfinal ioc : className="+this.getClass().getName()+"的"+f.getName()+"属性 <-- 已被注入. ");
					//递归注入
					injectUseBean(use_obj,use_class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 使用invoke来运行要执行的Method对象
	 * @param r
	 */
	public void runMethod(){
		//注入useBean
		injectUseBean(this,this.getClass().getName());//整合mgwork和mgioc的关键
		
		Method m = getMethodByActionName();
		if(m==null){
			//如果请求的方法不存在
			try {
				PrintWriter out = response.getWriter();
				String errorHtml = "<head><meta charset='utf-8'/><title>404</title></head><h2><center>404</center></h2>"
						+ "<hr/><center>您输入的请求有误，无法访问到资源，请重新输入!!<br/>shared by <a href='http://git.oschina.net/mgang/mgfinal'>mgfinal1.0</a></center>";
				out.print(errorHtml);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String res;
			try {
				res = (String) m.invoke(this);
				if(res!=null){
					//渲染模板
					handleViewType(res);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 渲染模板
	 */
	private void handleViewType(String view) {
		switch (MGFINAL_WEB_VIEW_TYPE) {
		case "jsp":
			renderJsp(view);
			break;
		case "freemarker":
			renderFreemarker(view);
			break;
		default:
			//默认jsp视图
			renderJsp(view);
			break;
		}
	}

	/**
	 * 封装request中参数为对象
	 * @param c 将表单参数要转成的对象类型，eg User.class
	 * @return 返回封装好的对象，通过fastjson to object实现
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object mgf2Object(Class c) {
		JSONObject json = new JSONObject();
		Map<String, String[]> p = this.request.getParameterMap();
		for(String key : p.keySet()){
			String[] val = p.get(key);
			if(val.length>1){
				//checkbox , mutli select
				//支持多表单属性，用英文逗号间隔
				String _v = "0";
				for(String v : val){
					_v += "," + v;
				}
				json.put(key, _v.substring(2));
			}else{
				if(val[0].length()>0) json.put(key, val[0]);
			}
		}
		return JSONObject.toJavaObject(json, c);
	}
	
	/**
	 * 封装request中参数为json
	 * @return JSONObject
	 */
	protected JSONObject mgf2Json() {
		JSONObject json = new JSONObject();
		Map<String, String[]> p = this.request.getParameterMap();
		for(String key : p.keySet()){
			String[] val = p.get(key);
			if(val.length>1){
				//checkbox , mutli select
				//支持多表单属性，用英文逗号间隔
				String _v = "0";
				for(String v : val){
					_v += "," + v;
				}
				json.put(key, _v.substring(2));
			}else{
				if(val[0].length()>0) json.put(key, val[0]);
			}
		}
		return json;
	}
	
	/**
	 * 封装request中参数为map
	 * @return Map
	 */
	protected Map<String,String> mgf2Map() {
		Map<String,String> map = new HashMap<String,String>();
		Map<String, String[]> p = this.request.getParameterMap();
		for(String key : p.keySet()){
			String[] val = p.get(key);
			if(val.length>1){
				//checkbox , mutli select
				//支持多表单属性，用英文逗号间隔
				String _v = "0";
				for(String v : val){
					_v += "," + v;
				}
				map.put(key, _v.substring(2));
			}else{
				if(val[0].length()>0) map.put(key, val[0]);
			}
		}
		return map;
	}
	/**增加request的方法**/
	protected void setAttr(String key,Object v) {
		this.request.setAttribute(key, v);
	}
	protected Object getAttr(String key) {
		return this.request.getAttribute(key);
	}
	protected void setSessionAttr(String key,Object v) {
		this.request.getSession().setAttribute(key, v);
	}
	protected Object getSessionAttr(String key) {
		return this.request.getSession().getAttribute(key);
	}
	/**增强获取参数方法**/
	protected String getPara(String key) {
		return this.request.getParameter(key);
	}
	protected Integer getParaToInt(String key) {
		return Integer.parseInt(this.request.getAttribute(key).toString().trim());
	}
	protected Float getParaToFloat(String key) {
		return Float.parseFloat(this.request.getAttribute(key).toString().trim());
	}
	protected Double getParaToDouble(String key) {
		return Double.parseDouble(this.request.getAttribute(key).toString().trim());
	}
	
	/**增强response方法**/
	protected void renderJson(Object obj) {
		try {
			this.response.setContentType("application/json;charset=utf-8");
			this.response.getOutputStream().write(JSONObject.toJSONString(obj).getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void ajaxJsonSuccess(Object obj) {
		AjaxResult ar = new AjaxResult();
		ar.setState(200);
		ar.setData(obj);
		ar.setMsg("操作成功");
		this.renderJson(ar);
	}
	protected void ajaxJsonError(Object obj) {
		AjaxResult ar = new AjaxResult();
		ar.setState(0);
		ar.setData(obj);
		ar.setMsg("操作失败");
		this.renderJson(ar);
	}
	/**
	 * 渲染jsp
	 * @param view
	 */
	protected void renderJsp(String view){
		//相对路径，绝对路径，后缀支持
		String tourl = view;
		if(!view.substring(0,1).equals("/")) tourl = MGFINAL_WEBFLOADER_PREFIX + "/" + tourl;
		if(!view.contains(".")) tourl = tourl + ".jsp";
		try {
			request.getRequestDispatcher(tourl).forward(request,response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * freemarker支持
	 */
	protected void renderFreemarker(String tpl) {
		//获取request中的attributes到data中，然后给freemarker渲染
		Map<String,Object> data = new HashMap<String,Object>();
		//封装request中的参数
		for (Enumeration<String> attrs=request.getAttributeNames(); attrs.hasMoreElements();) {
			String attrName = attrs.nextElement();
			data.put(attrName, request.getAttribute(attrName));
		}
		//封装session中参数
		for (Enumeration<String> attrs=request.getSession().getAttributeNames(); attrs.hasMoreElements();) {
			String attrName = attrs.nextElement();
			data.put(attrName, request.getSession().getAttribute(attrName));
		}
		//不延迟加载
		cfg.setTemplateUpdateDelay(0);
		//设置模板加载目录前缀
		cfg.setServletContextForTemplateLoading(getServletContext(),MGFINAL_WEBFLOADER_PREFIX);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //设置默认格式化样式
        cfg.setNumberFormat("#0.#####");
        cfg.setDateFormat("yyyy-MM-dd");
        cfg.setTimeFormat("HH:mm:ss");
        cfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        Template temp;
        Writer out = null;
		try {
			String tourl = tpl;
			if(!tpl.contains(".")) tourl = tourl + MGFINAL_WEB_PAGE_STUFFIX;
			temp = cfg.getTemplate(tourl);
			out = new OutputStreamWriter(response.getOutputStream());
		    temp.process(data, out);
		    if(!tpl.substring(0,1).equals("/")) tourl = MGFINAL_WEBFLOADER_PREFIX + "/" + tourl;
			request.getRequestDispatcher(tourl);//跳转，并不转发，转发会出现异常
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	/**
	 * 初始化fileupload组件，会将表单信息和文件都初始化，可以通过相应的get方法来取得
	 * @throws FileUploadException
	 * @throws IOException
	 */
	protected void initFileUpload() throws FileUploadException, IOException {
		this.__form_field_map.clear();
		this.__form_file_map.clear();
        if (ServletFileUpload.isMultipartContent(request)){  
            DiskFileItemFactory factory = new DiskFileItemFactory();  
            factory.setSizeThreshold(1 * 1024 * 1024);
            ServletFileUpload sfu = new ServletFileUpload(factory);
            //设置文件约束
            //单个文件大小
            sfu.setFileSizeMax(MGFINAL_ALLOW_FILE_SIZE * 1024 * 1024);
            //总文件大小
            sfu.setSizeMax(MGFINAL_ALLOW_TOTAL_FILE_SIZE * 1024 * 1024);
            sfu.setHeaderEncoding("UTF-8");
            List<FileItem> files = sfu.parseRequest(request);
            int len = files.size();
            for(int i=0;i<len;i++) {
                FileItem item = files.get(i); // 从集合中获得一个文件流  
                // 如果是普通表单字段
                if(item.isFormField()) {
                    String name = item.getFieldName();// 获得该字段名称
                    String value = item.getString("utf-8");//获得该字段值
                    this.__form_field_map.put(name, value);
                }else if(item.getName().length()>0){// 如果为文件域
                    UFile ufile = new UFile();
                    //注意IE或FireFox中获取的文件名是不一样的，IE中是绝对路径，FireFox,google中只是文件名。
                    String fname = item.getName();
                    if(fname.indexOf("\\") != -1){
                    	fname = fname.substring(fname.lastIndexOf("\\")+1, fname.length());
                    }
                    ufile.setFilename(fname);
                    ufile.setInstream(item.getInputStream());
                    ufile.setSize(item.getSize());
                    this.__form_file_map.put(item.getFieldName(), ufile);//文件的话，存的是表单域名 - UFile对象
                }
            }  
        }
	}
	/**
	 * 获取普通表单信息
	 * @return
	 */
	protected Map<String,String> getFields(){
		return this.__form_field_map;
	}
	/**
	 * 获取文件表单信息
	 * @return
	 */
	protected Map<String,UFile> getFiles(){
		return this.__form_file_map;
	}
	/**
	 * 上传所有文件,必须先初始化fileupload组件，即调用initFileUpload()方法
	 * @param saveDir 保存目录
	 * @throws IOException 
	 */
	protected void saveFiles(String saveDir) throws IOException{
		Map<String,UFile> ff = this.getFiles();
		if(ff.size()>0){
			for(String key : ff.keySet()){
				UFile ufile = ff.get(key);
				this.saveFile(ufile, saveDir);
			}
		}
	}
	/**
	 * 保存文件（上传文件）
	 * @param ufile 上传文件对象
	 * @param saveDir 保存文件的目录
	 * @throws IOException 
	 */
	protected void saveFile(UFile ufile, String saveDir) throws IOException {
		File dir = new File(saveDir);
		if(!dir.exists()) dir.mkdirs();
		FileUtils.copyInputStreamToFile(ufile.getInstream(), new File(dir,ufile.getFilename()));
	}
	/**
	 * 渲染文件，下载文件
	 * @param file
	 * @throws IOException
	 */
	protected void renderFile(File file) throws IOException{
		response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-disposition", "attachment;" + UFile.encodeFileName(request, file.getName()));
        FileUtils.copyFile(file, response.getOutputStream());
	}
}
