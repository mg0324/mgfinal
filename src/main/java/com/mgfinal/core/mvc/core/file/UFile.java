package com.mgfinal.core.mvc.core.file;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;


/**
 * 自定义上传文件实体
 * @author mg
 * @date 2016-5-23
 *
 */
public class UFile implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
	private InputStream instream;
	private long size;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getInstream() {
		return instream;
	}
	public void setInstream(InputStream instream) {
		this.instream = instream;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long l) {
		this.size = l;
	}
	/**
	 * 解决多浏览器文件名乱码
	 * @param request
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String encodeFileName(HttpServletRequest request, String fileName) {
	    String userAgent = request.getHeader("User-Agent");
	    String rtn = "";
	    try {
	        String new_filename = URLEncoder.encode(fileName, "UTF8");
	        // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
	        rtn = "filename=\"" + new_filename + "\"";
	        if (userAgent != null) {
	            userAgent = userAgent.toLowerCase();
	            // IE浏览器，只能采用URLEncoder编码
	            if (userAgent.indexOf("msie") != -1) {
	                rtn = "filename=\"" + new_filename + "\"";
	            }
	            // Opera浏览器只能采用filename*
	            else if (userAgent.indexOf("opera") != -1) {
	                rtn = "filename*=UTF-8''" + new_filename;
	            }
	            // Safari浏览器，只能采用ISO编码的中文输出
	            else if (userAgent.indexOf("safari") != -1) {
	                rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
	            }
	            // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
	            else if (userAgent.indexOf("applewebkit") != -1) {
	                new_filename = MimeUtility.encodeText(fileName, "UTF8", "B");
	                rtn = "filename=\"" + new_filename + "\"";
	            }
	            // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
	            else if (userAgent.indexOf("mozilla") != -1) {
	                rtn = "filename*=UTF-8''" + new_filename;
	            }
	        }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return rtn;
	}
}
