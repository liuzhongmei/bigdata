package com.itstaredu.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 */
public class TableMapper extends Mapper<LongWritable, Text,Text,TableBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        TableBean tableBean = new TableBean();
        Text k = new Text();
        //区分两张表
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        String name = inputSplit.getPath().getName();
        //获取数据
        String line = value.toString();
        //区分
        if(name.contains("order.txt")){//订单表
            //切分字段
            String[] fields = line.split("\t");

            //封装对象
            tableBean.setOrderId(fields[0]);
            tableBean.setPid(fields[1]);
            tableBean.setAmount(Integer.parseInt(fields[2]));
            tableBean.setPname("");
            tableBean.setFlag("0");

            k.set(fields[1]);
        }else{//商品表
            String[] fields = line.split("\t");

            tableBean.setOrderId("");
            tableBean.setPid(fields[0]);
            tableBean.setAmount(0);
            tableBean.setPname(fields[1]);
            tableBean.setFlag("1");
            k.set(fields[0]);
        }
        context.write(k,tableBean);
    }
}
