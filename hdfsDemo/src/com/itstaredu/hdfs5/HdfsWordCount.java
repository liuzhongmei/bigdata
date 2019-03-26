package com.itstaredu.hdfs5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 需求：文件（hello itstar hello hunter hello hunter henshuai）
 * 统计每个单词出现的次数
 * 2004google：dfs/bigtable/mapreduce
 *
 * 大数据解决的问题？
 *  1. 海量数据的存储-hdfs
 *  2. 海量数据的计算-mapreduce
 */
public class HdfsWordCount {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, InterruptedException {
        //反射
        Properties pro = new Properties();
        //加载配置文件
        pro.load(HdfsWordCount.class.getClassLoader().getResourceAsStream("job.properties"));

        Path inPath = new Path(pro.getProperty("IN_PATH"));
        Path outPath = new Path(pro.getProperty("OUT_PATH"));

        Class<?> mapperClass = Class.forName(pro.getProperty("MAPPER_CLASS"));
        //实例化
        Mapper mapper = (Mapper) mapperClass.newInstance();

        Context context = new Context();

        //1. 构建hdfs客户端对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"), conf, "root");

        //2. 读取用户输入的文件
        RemoteIterator<LocatedFileStatus> iter = fs.listFiles(inPath, false);

        while(iter.hasNext()){
            LocatedFileStatus file = iter.next();
            //打开路径获取输入流
            FSDataInputStream in = fs.open(file.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while((line = br.readLine()) != null){
                mapper.map(line,context);
            }
            br.close();
            in.close();
        }

        //如果用户输入的结果路径不存在 则创建
        Path path = new Path("/wc/out/");
        if(!fs.exists(path)){
            fs.mkdirs(path);
        }
        HashMap<Object, Object> contextMap = context.getContextMap();

        FSDataOutputStream out = fs.create(outPath);
        //遍历hashmap
        Set<Entry<Object, Object>> entrySet = contextMap.entrySet();
        for (Entry<Object, Object> entry:entrySet) {
            out.write((entry.getKey().toString() + ":" + entry.getValue().toString()+"\n").getBytes());
        }
        out.close();
        fs.close();
        System.out.println("数据结果统计完成");
    }
}
