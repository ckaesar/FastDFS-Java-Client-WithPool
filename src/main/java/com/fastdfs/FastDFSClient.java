package com.fastdfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
		
	public FastDFSClient(String trackerList, String pic_file_type, int pic_limited_size, String video_file_type, int video_limited_size) {
		try {
			String[] picTypeArray = pic_file_type.split(",");
			ClientUtil.picFileTypes = Arrays.asList(picTypeArray);
			ClientUtil.picLimitedSize = pic_limited_size;
			
			String[] videoTypeArray = video_file_type.split(",");
			ClientUtil.videoFileTypes = Arrays.asList(videoTypeArray);
			ClientUtil.videoLimitedSize = video_limited_size;
			
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
	 * 			；当flag为false时表示文件上传失败，msg为失败原因
	 */
	public ResultInfo uploadFile(byte[] file_buff, String fileName) {
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setFlag(false);
		
		//获取文件头、文件类型
		String fileCode = FileType.bytesToHexString(file_buff);
		String fileType = null; 
		if(fileCode == null) {
			resultInfo.setMsg("文件头为空，文件类型不符合规则");
			return resultInfo;
		} else {
			fileType = FileType.getFileTypeByFileCode(fileCode);
		}
		
		if(file_buff == null || file_buff.length == 0 || fileName == null){
			resultInfo.setMsg("文件内容或文件名称不能为空");
			return resultInfo;
		} else {
//			String[] fileNameArr = ClientUtil.splitFileName(fileName); //根据文件头判断文件的真实类型
			if(StringUtils.isBlank(fileType)){
				resultInfo.setMsg("文件扩展名不能为空！");
				return resultInfo;
			}else{
				if(ClientUtil.picFileTypes.contains(fileType)){
					if(file_buff.length > ClientUtil.picLimitedSize * 1024 * 1024) {
						resultInfo.setMsg("\"" + fileType + "\"" + ", 该类型最大可上传" + ClientUtil.picLimitedSize + "M" + "的文件");
						return resultInfo;
					} else {
						StorePath storePath = storageClient.uploadFile(file_buff, fileType);
						resultInfo.setMsg(storePath.getFullPath());
						resultInfo.setFlag(true);
						return resultInfo;
					}
				} else if(ClientUtil.videoFileTypes.contains(fileType)){
					if(file_buff.length > ClientUtil.videoLimitedSize * 1024 * 1024) {
						resultInfo.setMsg("\"" + fileType + "\"" + ", 该类型最大可上传" + ClientUtil.videoLimitedSize + "M" + "的文件");
						return resultInfo;
					} else {
						StorePath storePath = storageClient.uploadFile(file_buff, fileType);
						resultInfo.setMsg(storePath.getFullPath());
						resultInfo.setFlag(true);
						return resultInfo;
					}
				} else {
					resultInfo.setMsg("\"" + fileType + "\"" + "，禁止上传该类型的文件 ");
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
