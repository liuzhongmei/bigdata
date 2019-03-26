package com.itstaredu.pvcount;

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
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class PvCountSplitBolt extends BaseRichBolt {
    private OutputCollector collector;
    private int pvnum = 0;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    /**
     * 业务逻辑 分布式 集群 并发度 线程
     * @param input
     */
    @Override
    public void execute(Tuple input) {
        //1.获取数据
        String logs = input.getStringByField("logs");
        //2.切分
        String[] fields = logs.split("\t");
        //3.局部累加
        String sessionId = fields[1];
        if(sessionId != null){
            pvnum++;
            collector.emit(new Values(Thread.currentThread().getId(),pvnum));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //声明输出字段
        declarer.declare(new Fields("threadid","pvnum"));
    }
}
