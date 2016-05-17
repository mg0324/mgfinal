package com.demo.action;

import javax.servlet.annotation.WebServlet;

import com.jspsmart.upload.SmartUpload;
import com.mgfinal.core.mvc.core.BaseAction;
import com.mgfinal.utils.PathTool;
@WebServlet("/file.do/*")
public class FileAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String index(){
		
		return "file";
	}
	
	public String upload(){
		try{
			SmartUpload smart = initSmartUpload();
			String path = PathTool.getWebRootPath(request)+"file";
			smart.save(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "file";
	}

}
