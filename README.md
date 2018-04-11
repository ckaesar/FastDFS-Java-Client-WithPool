# FastDFS java Client

## maven配置
<dependency>    <br>
    &lt;groupId&gt;com.fastdfs&lt;/groupId&gt;  <br>
	&lt;artifactId>fastdfs-client&lt;/artifactId&gt; <br>
	&lt;version>0.0.1&lt;/version&gt;    <br>
</dependency>

## 配置文件添加如下配置：
##tracker地址，可以有多个，用逗号分隔开 <br>
tracker_server=10.10.2.118:22122,10.10.2.118:22122  <br>
#允许上传的文档、图片类型的文件扩展名   <br>
fastdfs_pic_file_type=doc,docx,pdf,jpg,png  <br>
##允许上传的文档、图片类型的最大文件大小限制    <br>
fastdfs_pic_limited_size=10 <br>
##允许上传的视频类型的文件扩展名    <br>
fastdfs_video_file_type=mp4,mov,3gp,avi,wmv <br>
##允许上传的视频类型的最大文件大小限制  <br>
fastdfs_video_limited_size=50   <br>
##查看文件的地址    <br>
FASTADDR=http://10.10.2.118/

## Spring配置文件相应配置：
&lt;!-- 初始化FastDFS配置 --&gt;  <br>
&lt;bean id="fastDFSClient" class="com.fastdfs.FastDFSClient"&gt; <br>  
	&lt;constructor-arg index="0" value="${tracker_server}"&gt;   <br>
	&lt;/constructor-arg&gt;  <br>
	&lt;constructor-arg index="1" value="${fastdfs_pic_file_type}"&gt;    <br>
	&lt;/constructor-arg&gt;  <br>
	&lt;constructor-arg index="2" value="${fastdfs_pic_limited_size}"&gt; <br>
	&lt;/constructor-arg&gt;  <br>
	&lt;constructor-arg index="3" value="${fastdfs_video_file_type}"&gt;  <br>
	&lt;/constructor-arg&gt;  <br>
	&lt;constructor-arg index="4" value="${fastdfs_video_limited_size}"&gt;   <br>
	&lt;/constructor-arg&gt;  <br>
&lt;/bean>

## java代码引用
@Autowired <br>
private FastDFSClient fastDFSClient;