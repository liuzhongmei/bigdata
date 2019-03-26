package com.itstaredu.flowpartition;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/16
 * @description:
 * @version: 1.0
 * 分区：根据手机号前三位进行分区
 * 输出5个结果文件：135  137  138  139  其他
 */
public class FlowCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job
        Job job = Job.getInstance();

        //2.获取jar包
        job.setJarByClass(FlowCountDriver.class);

        //3.获取自定义的mapper与reducer
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        //4.设置map输出的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //5.设置reducer输出的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置自定义的分区类
        job.setPartitionerClass(PhoneNumPartitioner.class);
        //大于分区数量
        job.setNumReduceTasks(5);

        //6.设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\flowIn"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\flowOut"));

        //7.提交任务
        boolean rs = job.waitForCompletion(true);
    }
}
