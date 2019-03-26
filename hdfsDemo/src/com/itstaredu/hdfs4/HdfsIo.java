package com.itstaredu.hdfs4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * IOUtils方式上传下载文件
 */
public class HdfsIo {
    Configuration conf = null;
    FileSystem fs = null;
    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"),conf,"root");
    }

    /**
     * 文件上传
     */
    @Test
    public void putFileToHDFS() throws IOException {
        // 获取输入流
        FileInputStream fis = new FileInputStream(new File("e:/啦啦啦.txt"));

        // 获取输出流
        FSDataOutputStream fos = fs.create(new Path("/啦啦啦.txt"), true);

        // 流的拷贝
        IOUtils.copyBytes(fis,fos,conf);

        // 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

    /**
     * 文件下载HDFS
     */
    @Test
    public void getFileFromHDFS() throws IOException {
        // 获取输入流
        FSDataInputStream fis = fs.open(new Path("/window.txt"));

        // 获取输出流
        FileOutputStream fos = new FileOutputStream("e:/window.txt");

        // 流的拷贝
        IOUtils.copyBytes(fis,fos,conf);
        // 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }
}
