package com.itstaredu.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/16
 * @description:
 * @version: 1.0
 * 思路：
 * wordcount单词计数
 * <单词,1>
 *
 * KEYIN:数据的起始偏移量
 * VALUEIN:数据
 * KEYOUT:mapper输出到reduce阶段k的类型
 * VALUEOUT:mapper输出到reduce阶段v的类型
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
    /**
     * 
     * @param key 起始偏移量
     * @param value 数据
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.读取数据
        String line = value.toString();
        //2.切割
        String[] words = line.split(" ");
        //3.循环的写到下一个阶段
        for (String word:words) {
            //shuchu到reduce阶段
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
