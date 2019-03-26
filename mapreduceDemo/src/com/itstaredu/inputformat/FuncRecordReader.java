package com.itstaredu.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 * 编写RecordReader
 */
public class FuncRecordReader extends RecordReader<NullWritable, BytesWritable> {
    boolean isProcess = false;
    FileSplit split;
    Configuration conf;
    BytesWritable value = new BytesWritable();
    /**
     * 初始化
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //初始化文件切片
        this.split = (FileSplit) split;
        //初始化配置信息
        this.conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue(){
        if(!isProcess){
            //1.根据切片的长度来创建缓冲区
            byte[] bytes = new byte[(int) split.getLength()];
            FSDataInputStream fis = null;
            FileSystem fs = null;
            //2.获取路径
            Path path = split.getPath();
            try {
                //3.根据路径获取文件系统
                fs = path.getFileSystem(conf);
                //4.拿到输入流
                fis = fs.open(path);
                //5.数据的拷贝
                IOUtils.readFully(fis,bytes,0,bytes.length);
                //6.拷贝缓存到最终的输出
                value.set(bytes,0,bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeStream(fis);
                IOUtils.closeStream(fs);
            }
            isProcess = true;
            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
