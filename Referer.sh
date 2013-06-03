#! /bin/bash -l

SELFPATH=`pwd`
SCRIPT=`readlink -f $0`
SCRIPTPATH=`dirname $SCRIPT`

cd $SCRIPTPATH
cd ../

if [ -z $1 ]
then
    echo "Usage sh $1 <inputPath> [outputPath]"
elif [ -z $2 ]
then
    d=$1
    d=${d:0:8}
    dout=${d}-out
else
    d=$1
    d=${d:0:8}
    dout=$2
fi

echo $d
echo $dout

# use for connect hbase
export HADOOP_CLASSPATH=$HBASE_HOME/hbase-0.92.1.jar:$HBASE_HOME/lib/zookeeper-\
3.4.3.jar:$HBASE_HOME/conf:$SELFPATH/lib/anjuke-ods-mr-1.0-SNAPSHOT.jar

REMOTE_FILE=/user/hadoop/alog/$d/[0-2][0-9]

hadoop jar jar/anjuke-ods-mr-1.0.0.jar com.anjuke.corp.ods.Referer $REMOTE_FILE ${dout}