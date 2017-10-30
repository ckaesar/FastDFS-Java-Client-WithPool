package com.fastdfs;

import java.util.List;


public class ClientUtil {
	
	public static String[] splitFileName(String fileName) {
		String[] fileStr = new String[2];
		fileStr[0] = fileName.substring(0, fileName.indexOf("."));
		fileStr[1] = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
		return fileStr;
	}
	
	//文件扩展名
	public static List<String> fileTypes = null;
}

