#关于Hadoop命令行

  Hadoop是由java编写的，因此，Hadoop命令归根结底是利用Java编译的，那么中间的转换是怎样的，不妨做了个实验。
  
  *** 
  
  打开`hadoop/vim/hadoop`文件，最后的执行语句：`exec "$JAVA" -Dproc_$COMMAND $JAVA_HEAP_MAX $HADOOP_OPTS -classpath`
    `"$CLASSPATH" $CLASS "$@"`,则表明hadoop命令行最终将命令转换为 `java jar -classpath CLASSPATH *.jar`,
同时引入hadoop库函数，搭建hadoop环境
  
