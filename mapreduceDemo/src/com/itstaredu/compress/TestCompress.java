package com.itstaredu.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;

/**
 * @author: lzm
 * @date: 2019/1/21
 * @description:
 * @version: 1.0
 */
public class TestCompress {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        comPress("E:\\hadoop数据测试\\window.txt","org.apache.hadoop.io.compress.BZip2Codec");
    }

    /**
     * 测试压缩方法
     * @param fileName
     * @param method
     */
    private static void comPress(String fileName,String method) throws IOException, ClassNotFoundException {
        //1.获取输入流
        FileInputStream fis = new FileInputStream(new File(fileName));

        Class<?> cName = Class.forName(method);
        CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(cName,new Configuration());

        //2.输出流
        FileOutputStream fos = new FileOutputStream(new File(fileName + codec.getDefaultExtension()));

        //3.创建压缩输出流
        CompressionOutputStream cos = codec.createOutputStream(fos);

        //4.流的对拷
        IOUtils.copyBytes(fis,cos,1024*1024*2,false);

        //5.关闭资源
        fis.close();
        cos.close();
        fos.close();
    }
}
