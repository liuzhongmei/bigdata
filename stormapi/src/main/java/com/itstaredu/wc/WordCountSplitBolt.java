package com.itstaredu.wc;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/2/25
 * @description:
 * @version: 1.0
 */
public class WordCountSplitBolt extends BaseRichBolt {
    private OutputCollector collector;
    /**
     * 初始化
     * @param map
     * @param topologyContext
     * @param outputCollector
     */
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    /**
     * 业务逻辑
     * @param tuple
     */
    @Override
    public void execute(Tuple tuple) {
        //1.获取数据
        String line = tuple.getStringByField("itstar");
        //2.切分数据
        String[] fields = line.split(" ");
        //3.<单词，1> 发送出去 下一个bolt（累加求和）
        for (String w:fields){
            collector.emit(new Values(w,1));
        }
    }

    /**
     * 声明描述
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word","sum"));
    }
}
