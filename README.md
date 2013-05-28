# hadoop-map-reduce-ex

这是一个Hadoop MapReduce的练习项目例子

## 目的

实现一个日志分析功能，统计单页访问中网页来源的分类统计（机器人、站内网页及站外网页）

## 快速开始

1. 请确认安装maven
2. 于根目录下，执行mvn install（请保证联网）
3. 执行mvn clean package命令生成对应的Jar包

## 运行

在Jar包根目录下，输入命令：

```
CLASS_PATH=lib/anjuke-ods-mr-1.0-SNAPSHOT.jar

hadoop jar target/hadoop-map-reduce-ex-1.0-SNAPSHOT.jar com.anjuke.corp.ods.alog.Referer <InputPath> [OutputPath]
```

**若未输入`OutputPath`参数，则输出结果默认当前路径，`InputPath + "-out"`文件夹内**
