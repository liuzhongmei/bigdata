package com.itstaredu.wccombiner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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
 * 单词统计  Combiner合并
 * job.setCombinerClass(WordCountCombiner.class);
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job信息
        Job job = Job.getInstance();

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

        //添加combiner
//        job.setCombinerClass(WordCountCombiner.class);
        job.setCombinerClass(WordCountReduce.class);

        //6.设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job,new Path("E:\\hadoop数据测试\\in"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\hadoop数据测试\\out2"));

        //7.提交任务
        boolean rs = job.waitForCompletion(true);
        System.out.println(rs?0:1);
    }
}
