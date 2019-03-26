package com.itstaredu.wc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author: lzm
 * @date: 2019/2/26
 * @description:
 * @version: 1.0
 */
public class WordCountDriver {
    public static void main(String[] args) {
        //创建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //指定设置
        builder.setSpout("WordCountSpout",new WordCountSpout(),1);
        builder.setBolt("WordCountSplitBolt",new WordCountSplitBolt(),4).fieldsGrouping("WordCountSpout",new Fields("itstar"));
        builder.setBolt("WordCountBolt",new WordCountBolt(),1).fieldsGrouping("WordCountSplitBolt",new Fields("word"));

        //3.创建配置信息
        Config conf = new Config();
        conf.setNumWorkers(10);

        //集群模式运行
//        try {
//            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
//        } catch (AlreadyAliveException e) {
//            e.printStackTrace();
//        } catch (InvalidTopologyException e) {
//            e.printStackTrace();
//        } catch (AuthorizationException e) {
//            e.printStackTrace();
//        }
        //4.提交任务
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("wordcounttopology", conf, builder.createTopology());
    }
}
