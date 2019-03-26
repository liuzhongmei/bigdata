package com.itstaredu.flowsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 * 分区：根据手机号前三位进行分区；输出5个结果文件：135  137  138  139  其他
 * 流量总和倒叙排序
 */
public class FlowSortDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        //获取jar包
        job.setJarByClass(FlowSortDriver.class);

        //获取mapper和reducer
        job.setMapperClass(FlowSortMapper.class);
        job.setReducerClass(FlowSortReducer.class);

        //设置mapper输出
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        //设置reducer输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //添加自定义分区
        job.setPartitionerClass(FlowSortPartitioner.class);
        job.setNumReduceTasks(5);

        //设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\\\flowSortIn"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\flowSortOut2"));

        //提交任务
        boolean rs = job.waitForCompletion(true);
    }
}
