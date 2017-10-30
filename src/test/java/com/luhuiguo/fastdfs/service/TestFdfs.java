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
		File file = new File("C:\\Users\\admin\\Pictures\\test_pic2.jpg");
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		byte[] file_buff = new byte[(int) file.length()];
		fis.read(file_buff);
		String trackerList = "10.10.2.118:22122";
		String fastdfs_file_type = "jpg";
		FastDFSClient client = new FastDFSClient(trackerList, fastdfs_file_type);
		
		ResultInfo resultInfo = client.uploadFile(file_buff, "testZip.jpg");
		System.out.println(resultInfo.getMsg());
	}
	
}
