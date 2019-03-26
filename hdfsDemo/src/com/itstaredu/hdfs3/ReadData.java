package com.itstaredu.hdfs3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class ReadData {
    public FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 客户端加载配置
        Configuration conf = new Configuration();
        // 设置副本数
//        conf.set("dfs.replication","2");
//        // 设置块大小
//        conf.set("dfs.blocksize","64m");

        // 构造客户端
        fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"), conf, "root");
    }

    /**
     * 读数据方式1
     */
    @Test
    public void testReadData1() throws IOException {
        FSDataInputStream in = fs.open(new Path("/hello/hello.txt"));
        byte[] buf = new byte[1024];
        in.read(buf);
        System.out.println(new String(buf));
        in.close();
        fs.close();
    }

    /**
     * 读数据方式2
     */
    @Test
    public void testReadData2() throws IOException {
        // 获取流
        FSDataInputStream in = fs.open(new Path("/hello/hello.txt"));

        // 缓冲流
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        // 按行读取
        String line = null;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        // 关闭流
        br.close();
        in.close();
        fs.close();
    }

    /**
     * 读取指定偏移量数据
     */
    @Test
    public void testRandomRead() throws IOException {
        FSDataInputStream in = fs.open(new Path("/hello/hello.txt"));
        in.seek(8);
        byte[] bytes = new byte[9];
        in.read(bytes);
        System.out.println(new String(bytes));
        // 关闭流
        in.close();
        fs.close();
    }

    /**
     * 在hdfs中写数据
     */
    @Test
    public void testWriteData() throws IOException {
        // 输出流
        FSDataOutputStream out = fs.create(new Path("/window.txt"), false);

        // 输入流
        FileInputStream in = new FileInputStream("e:/words.txt");
        byte[] bytes = new byte[1024];
        int b = 0;
        while(-1 != (b = in.read(bytes))){
            out.write(bytes,0,b);
        }
        in.close();
        out.close();
        fs.close();
    }

    /**
     * 在hdfs中写数据
     */
    @Test
    public void testWriteData1() throws IOException {
        // 创建输出流
        FSDataOutputStream out = fs.create(new Path("/write.txt"), true);

        // 写数据
        out.write("拉阿鲁啦啦啦".getBytes());

        // 关闭资源
        out.close();
        fs.close();
    }
}
