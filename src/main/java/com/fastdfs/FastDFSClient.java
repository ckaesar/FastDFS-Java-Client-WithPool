package com.fastdfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;


public class FastDFSClient {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(FastDFSClient.class);
	
	FdfsConnectionPool pool = null;
    List<String> trackers = new ArrayList<>();
    TrackerConnectionManager tcm = null;
    TrackerClient trackerClient = null;
    ConnectionManager cm = null;
    FastFileStorageClient storageClient = null;
		
	public FastDFSClient(String trackerList, String fastdfs_file_type) {
		try {
			String[] fileTypeArray = fastdfs_file_type.split(",");
			ClientUtil.fileTypes = Arrays.asList(fileTypeArray);
			
			String[] trackerStrings = trackerList.split(",");
			trackers = Arrays.asList(trackerStrings);
			pool = new FdfsConnectionPool();
			tcm = new TrackerConnectionManager(pool, trackers);
			trackerClient = new DefaultTrackerClient(tcm);
			cm = new ConnectionManager(pool);
			storageClient = new DefaultFastFileStorageClient(trackerClient, cm);
		} catch (Exception e) {
			LOGGER.debug("FastDFS初始化失败：" + e);
		}
	}
	
	/**
	 * FastDFS上传附件
	 * @param file_buff 文件内容
	 * @param fileName 文件名，例如："test.jpg"
	 * @return 返回ResultInfo，当flag为true时表示文件上传成功，msg为文件id，例如："group1/M00/00/00/CgoCd1mtEeOACdAcAAMddT8sm7I383.png"
	 */
	public ResultInfo uploadFile(byte[] file_buff, String fileName) {
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setFlag(false);
		if(file_buff == null || file_buff.length == 0 || fileName == null){
			resultInfo.setMsg("文件内容或文件名称不能为空");
			return resultInfo;
		}else{
			String[] fileNameArr = ClientUtil.splitFileName(fileName);
			if(fileNameArr.length == 1 || fileNameArr[1] == null){
				resultInfo.setMsg("文件扩展名不能为空");
				return resultInfo;
			}else{
				if(!ClientUtil.fileTypes.contains(fileNameArr[1])){
					resultInfo.setMsg("\"" + fileNameArr[1] + "\"" + "，禁止上传该类型的文件 ");
					return resultInfo;
				}else{
					StorePath storePath = storageClient.uploadFile(file_buff, fileNameArr[1]);
					resultInfo.setMsg(storePath.getFullPath());
					resultInfo.setFlag(true);
					return resultInfo;
				}
			}
			
		}
		
		
	}
	
	/**
	 * 根据文件id下载文件
	 * @param fileId 文件id，例如："group1/M00/00/00/CgoCd1mtEeOACdAcAAMddT8sm7I383.png"
	 * @return 返回文件内容
	 */
	public byte[] downloadFile(String fileId) {
		StorePath storePath = StorePath.praseFromUrl(fileId);
		return storageClient.downloadFile(storePath.getGroup(), storePath.getPath());
	}
	
	/**
	 * 根据文件id删除文件
	 * @param fileId 文件id
	 */
	public void deleteFile(String fileId) {
		storageClient.deleteFile(fileId);
	}
	
}
