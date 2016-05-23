package com.demo.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.alibaba.fastjson.JSONObject;
import com.demo.vo.MFile;
import com.mgfinal.core.mvc.core.BaseAction;
import com.mgfinal.core.mvc.core.file.UFile;
import com.mgfinal.utils.PathTool;
@WebServlet("/file.do/*")
public class FileAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String index(){
		//查询已上传文件
		String path = PathTool.getWebRootPath(request);
		path = path +"/file";
		File dir = new File(path);
		List<MFile> arr = new ArrayList<MFile>();
		if(dir.exists()){
			for(File f : dir.listFiles()){
				MFile file = new MFile();
				file.setName(f.getName());
				file.setPath(f.getPath());
				arr.add(file);
			}
		}
		this.setAttr("files", arr);
		return "file";
	}
	
	public void upload() throws Exception{
		//初始化上传组件
		this.initFileUpload();
		Map<String,UFile> f = this.getFiles();
		UFile ufile = f.get("file1");
		this.saveFile(ufile,PathTool.getWebRootPath(request)+"/file/");
		
		System.out.println(JSONObject.toJSONString(f));
	}
	
	public void download(){
		String name = this.getPara("name");
		try {
			name = URLDecoder.decode(name,"utf-8").toString();
			File f = new File(PathTool.getWebRootPath(request)+"/file/"+name);
			this.renderFile(f);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
