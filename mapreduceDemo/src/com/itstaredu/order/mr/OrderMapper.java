package com.itstaredu.order.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class OrderMapper extends Mapper<LongWritable, Text,OrderBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取每行数据
        String line = value.toString();

        //2.切割
        String[] fields = line.split("\t");

        //3.取出关键字段
        Integer orderId = Integer.parseInt(fields[0]);
        Double price = Double.parseDouble(fields[2]);

        //4.输出
        context.write(new OrderBean(orderId,price),NullWritable.get());
    }
}
