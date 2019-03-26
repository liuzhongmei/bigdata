package com.itstaredu.mapjoin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author Hunter
 * @date 2018年10月29日 下午9:42:01
 * @version 1.0
 */
public class CacheDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        // 1.获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.获取jar包
        job.setJarByClass(CacheDriver.class);

        // 3.获取自定义的mapper与reducer类
        job.setMapperClass(CacheMapper.class);

        // 5.设置reduce输出的数据类型（最终的数据类型）
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 6.设置输入存在的路径与处理后的结果路径
        FileInputFormat.setInputPaths(job, new Path("E:\\hadoop数据测试\\tableIn"));
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop数据测试\\tableOut"));

        //加载缓存商品数据
        job.addCacheFile(new URI("file:///E:/hadoop数据测试/inputcache/pd.txt"));

        //设置一下reducetask的数量
        job.setNumReduceTasks(0);

        // 7.提交任务
        boolean rs = job.waitForCompletion(true);
        System.out.println(rs ? 0 : 1);
    }
}
