package com.itstaredu.order.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class OrderDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.获取jar包
        job.setJarByClass(OrderDriver.class);

        //3.获取mapper和reducer
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);

        //4.设置mapper输出
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        //5.设置reducer输出
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        //6.设置reducer端的分组
        job.setGroupingComparatorClass(OrderGroupingComparator.class);

        //7.设置分区
        job.setPartitionerClass(OrderPartitioner.class);

        //8.设置reduceTask个数
        job.setNumReduceTasks(4);

        //9.设置输入输出数据
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\OrderIn"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\OrderOut"));

        //10.提交任务
        boolean rs = job.waitForCompletion(true);
        System.out.println(rs ? 0 : -1);
    }
}
