package com.itstaredu.mapjoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.net.URI;
import java.util.HashMap;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 * 商品表加载到内存中，然后数据在map端输出前进行替换
 */
public class CacheMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

    HashMap<String, String> pdMap = new HashMap<>();
    @Override
    //1.商品表加载到内存
    protected void setup(Context context) throws IOException {
        URI[] cacheFiles = context.getCacheFiles();
        URI cacheFile = cacheFiles[0];
        //加载缓存文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile.getPath()), "Utf-8"));

        String line;

        while(StringUtils.isNotEmpty(line = br.readLine()) ) {

            //切分
            String[] fields = line.split("\t");

            //缓存
            pdMap.put(fields[0], fields[1]);

        }

        br.close();

    }



    //2.map传输
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
            throws IOException, InterruptedException {
        //获取数据
        String line = value.toString();

        //切割
        String[] fields = line.split("\t");

        //获取订单中商品id
        String pid = fields[1];

        //根据订单商品id获取商品名
        String pName = pdMap.get(pid);

        //拼接数据
        line = line + "\t" + pName;

        //输出
        context.write(new Text(line), NullWritable.get());
    }
}