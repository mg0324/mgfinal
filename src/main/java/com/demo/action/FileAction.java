package com.demo.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.demo.vo.MFile;
import com.mgfinal.core.mvc.core.BaseAction;
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
	
	public String upload() throws Exception{
		//初始化上传组件
		this.initFileUpload();
		//上传所有文件
		this.saveFiles(PathTool.getWebRootPath(request)+"/file/");
		return index();
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
