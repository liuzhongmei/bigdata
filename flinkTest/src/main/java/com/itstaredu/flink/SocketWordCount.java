package com.itstaredu.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author: lzm
 * @date: 2019/2/27
 * @description:
 * @version: 1.0
 * 实时的wordcount
 * 在端口中发送数据，实时的计算数据
 */
public class SocketWordCount {
    public static void main(String[] args) throws Exception {
        //1.定义连接端口
        final int port = 3333;
        //2.创建执行环境对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //3.得到套接字对象
        DataStreamSource<String> text = env.socketTextStream("192.168.230.201", port, "\n");
        //4.解析数据，统计数据-单词计数
        DataStream<WordCount> windowCount = text.flatMap(new FlatMapFunction<String, WordCount>() {
            public void flatMap(String s, Collector<WordCount> collector) throws Exception {
                for (String word : s.split("\\s")) {
                    //<单词，1>
                    collector.collect(new WordCount(word, 1L));
                }
            }
        })
                //按照key进行分组
                .keyBy("word")
                //设置窗口的时间长度  5秒一次窗口 1秒计算一次
                .timeWindow(Time.seconds(5), Time.seconds(1))
                //聚合，聚合函数
                .reduce(new ReduceFunction<WordCount>() {
                    public WordCount reduce(WordCount a, WordCount b) throws Exception {
                        return new WordCount(a.word, a.count + b.count);
                    }
                });
        //5.打印
        windowCount.print().setParallelism(1);
        //6.执行程序
        env.execute("Socket window WordCount");
    }


    public static class WordCount{
        public String word;
        public long count;

        public WordCount() {
        }

        public WordCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
