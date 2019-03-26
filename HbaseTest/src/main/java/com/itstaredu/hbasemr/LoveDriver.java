package com.itstaredu.hbasemr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author: lzm
 * @date: 2019/2/22
 * @description:
 * @version: 1.0
 */
public class LoveDriver implements Tool {
    private Configuration conf;
    public int run(String[] strings) throws Exception {
        //1.创建任务
        Job job = Job.getInstance(conf);
        //2.指定运行的主类
        job.setJarByClass(LoveDriver.class);
        //3.配置job 采用scan方式扫描该表
        Scan scan = new Scan();
        //4.设置mapper类
        TableMapReduceUtil.initTableMapperJob("love",
                scan,
                ReadLoveMapper.class,
                ImmutableBytesWritable.class,
                Put.class,
                job);

        //5.设置reducer类
        TableMapReduceUtil.initTableReducerJob("lovemr",
                WriteLoveReducer.class,
                job
        );

        //设置reduceTask个数
        job.setNumReduceTasks(1);

        boolean rs = job.waitForCompletion(true);
        return rs?0:1;
    }

    public void setConf(Configuration configuration) {
        this.conf = HBaseConfiguration.create(configuration);
    }

    public Configuration getConf() {
        return this.conf;
    }

    public static void main(String[] args) {
        try {
            int status = ToolRunner.run(new LoveDriver(),args);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
