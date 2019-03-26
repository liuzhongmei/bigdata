package com.itstaredu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClientDemo2 {
    public static void main(String[] args) throws URISyntaxException, IOException,InterruptedException {
        // 客户端加载配置
        Configuration conf = new Configuration();
        // 设置副本数
        conf.set("dfs.replication","1");
        // 设置块大小
        conf.set("dfs.blocksize","64m");
        // 构造客户端
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"),conf,"root");

        // 上传文件
//        fs.copyFromLocalFile(new Path("e:/words.txt"),new Path("/wordbbb.txt"));
        /**
         * 下载hdfs文件
         * java.io.FileNotFoundException: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset.
         * 配置HADOOP环境变量
         * java.io.FileNotFoundException: Could not locate Hadoop executable: E:\software_package\hadoop-2.8.4\bin\winutils.exe
         * 需要将winutils.exe文件拷贝到 E:\software_package\hadoop-2.8.4\bin目录下
         *    下载的是linux环境的tar包，解压之后直接用的，需要导入编译好的windows环境
         */
        fs.copyToLocalFile(new Path("/aa.txt"),new Path("e:/"));
        // 关闭资源
        fs.close();

    }
}
