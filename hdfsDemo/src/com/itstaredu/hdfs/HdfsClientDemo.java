package com.itstaredu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lzm
 */
public class HdfsClientDemo {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        //1. 客户端加载配置
        Configuration con = new Configuration();

        //2. 指定配置(2个副本数)
        con.set("dfs.replication","2");

        //3. 指定块大小
        con.set("dfs.blocksize","64m");

        //4. 构造客户端
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"),con,"root");

        //5. 上传文件
        fs.copyFromLocalFile(new Path("e:/words.txt"),new Path("/wordaaa.txt"));

        //6. 关闭资源
        fs.close();
    }
}
