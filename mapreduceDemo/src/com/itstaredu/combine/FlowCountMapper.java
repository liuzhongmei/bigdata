package com.itstaredu.combine;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/16
 * @description:
 * @version: 1.0
 * 输入 3631279930353	13560436326	C4-17-FE-BA-DE-D9:CMCC	120.196.100.77	lol.qq.com/	英雄联盟	18	15	1136	94	200
 * 输出 13560436326 1136 94 1230
 */
public class FlowCountMapper extends Mapper<LongWritable, Text, Text , FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取数据
        String line = value.toString();

        //2.切割
        String[] words = line.split("\t");

        //3.封装对象 拿到关键字段
        String phoneNum = words[1];
        long upFlow = Long.parseLong(words[words.length - 3]);
        long dfFlow = Long.parseLong(words[words.length - 2]);
        //4.输出到reducer端
        context.write(new Text(phoneNum),new FlowBean(upFlow,dfFlow));
    }
}
