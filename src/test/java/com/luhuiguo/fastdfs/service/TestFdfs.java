package com.luhuiguo.fastdfs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.fastdfs.FastDFSClient;
import com.fastdfs.ResultInfo;

public class TestFdfs {
	
	@Test
	public void testInit() throws IOException {
		File file = new File("C:\\Users\\admin\\Pictures\\保险代理系统需求说明书v1.1.docx");
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		byte[] file_buff = new byte[(int) file.length()];
		fis.read(file_buff);
		String trackerList = "10.10.2.118:22122";
		String pic_file_type = "doc,docx,pdf,jpg,png";
		String video_file_type = "mp4,mov,3gp,avi,wmv";
		FastDFSClient client = new FastDFSClient(trackerList, pic_file_type, 10, video_file_type, 50);
		
		ResultInfo resultInfo = client.uploadFile(file_buff, "保险代理系统需求说明书v1.1.docx");
		System.out.println(resultInfo.getMsg());
	}
	
}
