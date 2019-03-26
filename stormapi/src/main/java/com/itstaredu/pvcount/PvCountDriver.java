package com.itstaredu.pvcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.testing.TupleCaptureBolt;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author: lzm
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class PvCountDriver {
    public static void main(String[] args) {
        //创建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //指定设置
        builder.setSpout("PvCountSpout",new PvCountSpout(),1);
        builder.setBolt("PvCountSplitBolt",new PvCountSplitBolt(),6).setNumTasks(4).shuffleGrouping("PvCountSpout");
        builder.setBolt("PvCountSumBolt",new PvCountSumBolt(),1).shuffleGrouping("PvCountSplitBolt");

        //3.创建配置信息
        Config conf = new Config();
        conf.setNumWorkers(4);

        //4.提交任务
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("wordcounttopology", conf, builder.createTopology());
    }
}
