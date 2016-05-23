package com.demo.action;

import java.io.File;
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
		path = PathTool.getParentPath(path,2)+"/file";
		File dir = new File(path);
		List<MFile> arr = new ArrayList<MFile>();
		for(File f : dir.listFiles()){
			MFile file = new MFile();
			file.setName(f.getName());
			file.setPath(f.getPath());
			arr.add(file);
		}
		this.setAttr("files", arr);
		return "file";
	}
	
	


}
