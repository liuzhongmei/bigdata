package com.itstaredu.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/16
 * @description: 压缩
 * @version: 1.0
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        //1.获取job信息
        Job job = Job.getInstance(conf);

        //开启map端的输出压缩
        conf.setBoolean("mapreduce.map.output.compress",true);
        //设置压缩方式
//        conf.setClass("mapreduce.map.output.compress.codec", DefaultCodec.class, CompressionCodec.class);
        conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class,CompressionCodec.class);

        //2.获取jar包
        job.setJarByClass(WordCountDriver.class);

        //3.获取自定义的mapper与reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);

        //4.设置map输出的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置reduce输出的数据类型
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(IntWritable.class);

        //开启reduce端的输出压缩
        FileOutputFormat.setCompressOutput(job,true);
        //设置压缩格式
        FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

        //6.设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\in"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\out"));

        //7.提交任务
        boolean rs = job.waitForCompletion(true);
        System.out.println(rs?0:1);
    }
}
