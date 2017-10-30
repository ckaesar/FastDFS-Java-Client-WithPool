package com.luhuiguo.fastdfs.service;

import java.io.InputStream;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.domain.StorageNode;
import com.luhuiguo.fastdfs.domain.StorageNodeInfo;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.proto.storage.StorageAppendFileCommand;
import com.luhuiguo.fastdfs.proto.storage.StorageModifyCommand;
import com.luhuiguo.fastdfs.proto.storage.StorageTruncateCommand;
import com.luhuiguo.fastdfs.proto.storage.StorageUploadFileCommand;

/**
 * 存储服务客户端接口实现
 * 
 * @author tobato
 *
 */
public class DefaultAppendFileStorageClient extends DefaultGenerateStorageClient implements AppendFileStorageClient {

    public DefaultAppendFileStorageClient(TrackerClient trackerClient,
      ConnectionManager connectionManager) {
    super(trackerClient, connectionManager);
  }

    /**
     * 上传支持断点续传的文件
     */
    @Override
    public StorePath uploadAppenderFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        StorageNode client = trackerClient.getStoreStorage(groupName);
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream,
                fileExtName, fileSize, true);
        return connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
    }

    /**
     * 继续上载文件
     */
    @Override
    public void appendFile(String groupName, String path, InputStream inputStream, long fileSize) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageAppendFileCommand command = new StorageAppendFileCommand(inputStream, fileSize, path);
        connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
    }

    /**
     * 修改文件
     */
    @Override
    public void modifyFile(String groupName, String path, InputStream inputStream, long fileSize, long fileOffset) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageModifyCommand command = new StorageModifyCommand(path, inputStream, fileSize, fileOffset);
        connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);

    }

    /**
     * 清除文件
     */
    @Override
    public void truncateFile(String groupName, String path, long truncatedFileSize) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageTruncateCommand command = new StorageTruncateCommand(path, truncatedFileSize);
        connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
    }

    /**
     * 清除文件
     */
    @Override
    public void truncateFile(String groupName, String path) {
        long truncatedFileSize = 0;
        truncateFile(groupName, path, truncatedFileSize);
    }

}
