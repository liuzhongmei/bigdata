package com.itstaredu.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 */
public class FileDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //获取jar包
        job.setJarByClass(FileDriver.class);

        //获取mapper和reducer
        job.setMapperClass(FileMapper.class);
        job.setReducerClass(FileReducer.class);

        //设置mapper输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        //设置reducer输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置自定义的outputformat
        job.setOutputFormatClass(FuncFileOutputFormat.class);

        //设置输入文件和输出文件路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\fileIn2"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\fileOut2"));

        //提交任务
        boolean rs = job.waitForCompletion(true);

    }
}
