package com.itstaredu.wc;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class WordCountBolt extends BaseRichBolt {

    Map<String,Integer> map = new HashMap<String,Integer>();
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    }

    @Override
    public void execute(Tuple tuple) {
        //获取数据
        String word = tuple.getStringByField("word");
        Integer sum = tuple.getIntegerByField("sum");

        //业务处理
        if(map.containsKey(word)){
            map.put(word,map.get(word)+sum);
        }else{
            map.put(word,sum);
        }
        //3.打印控制台
        System.err.println(Thread.currentThread().getName() + "\t 单词为：" + word + "\t 当前已出现次数为：" + map.get(word));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
