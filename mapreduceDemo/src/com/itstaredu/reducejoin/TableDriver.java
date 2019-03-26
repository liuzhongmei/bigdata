package com.itstaredu.reducejoin;

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
public class TableDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //获取jar包
        job.setJarByClass(TableDriver.class);

        //获取mapper和reducer
        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);

        //设置mapper输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        //设置reducer输出
        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);

        //设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\tableIn2"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\tableOut2"));

        //提交任务
        boolean rs = job.waitForCompletion(true);
        System.out.println(rs ? 0 : -1);
    }
}
