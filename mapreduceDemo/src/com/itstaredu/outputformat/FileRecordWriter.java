package com.itstaredu.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 */
public class FileRecordWriter extends RecordWriter<Text, NullWritable> {
    Configuration conf = null;
    FSDataOutputStream itstarLog = null;
    FSDataOutputStream otherLog = null;
    //1.定义数据输出路径
    public FileRecordWriter(TaskAttemptContext context) throws IOException {
        //获取配置信息
        conf = context.getConfiguration();

        //获取文件系统
        FileSystem fs = FileSystem.get(conf);

        //定义输出路径
        itstarLog = fs.create(new Path("E:\\hadoop数据测试\\fileOut2\\itstar.logs"));
        otherLog = fs.create(new Path("E:\\hadoop数据测试\\fileOut2\\other.logs"));
    }

    //2.数据输出
    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        if(key.toString().contains("itstar")){
            itstarLog.write(key.getBytes());
        }else{
            otherLog.write(key.getBytes());
        }
    }

    //3.关闭资源
    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        if(null != itstarLog){
            itstarLog.close();
        }
        if(null != otherLog){
            otherLog.close();
        }
    }
}
