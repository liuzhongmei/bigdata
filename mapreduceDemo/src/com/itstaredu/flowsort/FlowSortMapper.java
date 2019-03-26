package com.itstaredu.flowsort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class FlowSortMapper extends Mapper<LongWritable, Text,FlowBean,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取数据
        String line = value.toString();

        //2.数据切割
        String[] words = line.split("\t");

        //3.取出字段
        String phonenum = words[0];
        Long upFlow = Long.parseLong(words[1]);
        Long dfFlow = Long.parseLong(words[2]);

        //4.写出到reducer
        context.write(new FlowBean(upFlow,dfFlow),new Text(phonenum));
    }
}
