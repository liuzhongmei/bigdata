package com.itstaredu.wccombiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 * 局部汇总
 */
public class WordCountCombiner extends Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //1.计数
        int count = 0;

        //2.累加求和
        for(IntWritable v:values){
            count += v.get();
        }

        //结果输出
        context.write(key,new IntWritable(count));
    }
}
