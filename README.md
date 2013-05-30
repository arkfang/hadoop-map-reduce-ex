# hadoop-map-reduce-ex

这是一个Hadoop MapReduce的练习项目例子

***

## 目的

实现一个日志分析功能，统计单页访问中网页来源的分类统计（机器人、站内网页及站外网页）


## 快速开始

1. 搭建maven环境：`sudo aptitude install maven2`或手动安装，[下载](http://maven.apache.org/)。
2. 搭建hadoop运行环境:`sudo aptitude install hadoop`或手动安装，[下载](http://hadoop.apache.org/)。
3. 打开`.bashrc`，添加环境变量

 ```
 export HADOOP_HOME=home/${username}/Downloads/hadoop

 export PATH=$PATH:$HADOOP_HOOME/bin
 ```
4. 于源代码根目录，执行`mvn install`（请保证联网）
5. 执行`mvn package`编译生成`target/hadoop-map-reduce-ex-1.0-SNAPSHOT.jar`


## 运行
  
程序根目录下，执行命令：

```
HADOOP_CLASSPATH=lib/anjuke-ods-mr-1.0-SNAPSHOT.jar

hadoop jar target/hadoop-map-reduce-ex-1.0.0.jar com.anjuke.corp.ods.alog.Referer <InputPath> [OutputPath]
```

**若未输入`OutputPath`参数，则输出结果默认当前路径，`InputPath + "-out"`文件夹内**
