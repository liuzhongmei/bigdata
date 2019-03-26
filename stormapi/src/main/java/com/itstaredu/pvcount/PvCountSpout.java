package com.itstaredu.pvcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.*;
import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class PvCountSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private BufferedReader br;
    private String line;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        //读取文件
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("e:/weblog.log")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        try {
            while(null != (line = br.readLine())) {
                collector.emit(new Values(line));
                Thread.sleep(500);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logs"));
    }
}
