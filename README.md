# FastDFS-Java-Client-WithPool
FastDFS Client Java客户端代码，带有连接池

# maven配置
<dependency>
	<groupId>com.fastdfs</groupId>
	<artifactId>fastdfs-client</artifactId>
	<version>0.0.1</version>
</dependency>

# 配置文件添加如下配置：
##tracker地址，可以有多个，用逗号分隔开
tracker_server=10.10.2.118:22122,10.10.2.118:22122
#允许上传的文档、图片类型的文件扩展名
fastdfs_pic_file_type=doc,docx,pdf,jpg,png
##允许上传的文档、图片类型的最大文件大小限制
fastdfs_pic_limited_size=10
##允许上传的视频类型的文件扩展名
fastdfs_video_file_type=mp4,mov,3gp,avi,wmv
##允许上传的视频类型的最大文件大小限制
fastdfs_video_limited_size=50
##查看文件的地址
FASTADDR=http://10.10.2.118/

# Spring配置文件相应配置：
<!-- 初始化FastDFS配置 -->
<bean id="fastDFSClient" class="com.fastdfs.FastDFSClient">
	<constructor-arg index="0" value="${tracker_server}">
	</constructor-arg>
	<constructor-arg index="1" value="${fastdfs_pic_file_type}">
	</constructor-arg>
	<constructor-arg index="2" value="${fastdfs_pic_limited_size}">
	</constructor-arg>
	<constructor-arg index="3" value="${fastdfs_video_file_type}">
	</constructor-arg>
	<constructor-arg index="4" value="${fastdfs_video_limited_size}">
	</constructor-arg>
</bean>

# java代码引用
@Autowired
private FastDFSClient fastDFSClient;