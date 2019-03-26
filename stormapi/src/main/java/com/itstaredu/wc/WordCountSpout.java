package com.itstaredu.wc;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/2/25
 * @description:
 * @version: 1.0
 * 需求：单词计数
 *
 *
 * 接口：IRichSpout  IRichBolt
 * 抽象类:BaseRichSpout  BaseRichBolt 常用
 */
public class WordCountSpout extends BaseRichSpout {
    //定义收集器
    private SpoutOutputCollector collector;
    /**
     * 创建收集器
     * @param map
     * @param topologyContext
     * @param spoutOutputCollector
     */
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    /**
     * 发送数据
     */
    @Override
    public void nextTuple() {
        //1. 发送数据
        collector.emit(new Values("i am hunter very shuai"));
        //2.设置延迟
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 声明描述
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("itstar"));
    }
}
