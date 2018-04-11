package com.fastdfs;

import java.util.List;


public class ClientUtil {
	
	public static String[] splitFileName(String fileName) {
		String[] fileStr = new String[2];
		if(fileName.contains(".")){
			fileStr[0] = fileName.substring(0, fileName.lastIndexOf("."));
			fileStr[1] = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}else{
			fileStr[0] = "";
			fileStr[1] = "";
		}
		return fileStr;
		
	}
	
	//文件扩展名
	public static List<String> fileTypes = null;
	
	//图片、文档类文件类型
	public static List<String> picFileTypes = null;
	
	//当文件类型为picFileTypes时，文件大小限制
	public static int picLimitedSize;
	
	//视频类文件类型
	public static List<String> videoFileTypes = null;
	
	//当文件类型为videoFileTypes时，文件大小限制 
	public static int videoLimitedSize;
}

