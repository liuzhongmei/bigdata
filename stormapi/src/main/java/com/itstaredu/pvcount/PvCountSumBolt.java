package com.itstaredu.pvcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class PvCountSumBolt extends BaseRichBolt {
    private HashMap<Long,Integer> map = new HashMap<Long, Integer>();
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        //1.获取数据
        Long threadid = input.getLongByField("threadid");
        Integer pvnum = input.getIntegerByField("pvnum");
        //2.创建集合
        map.put(threadid,pvnum);
        //3.累加求和(拿到集合中所有value值)
        Iterator<Integer> iterator = map.values().iterator();
        int sumnum = 0;
        while(iterator.hasNext()){
            sumnum += iterator.next();
        }
        System.err.println(Thread.currentThread().getName()+"总访问量为"+sumnum);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
