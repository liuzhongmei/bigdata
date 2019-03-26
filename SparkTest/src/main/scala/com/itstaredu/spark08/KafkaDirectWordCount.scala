package com.itstaredu.spark08

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, StreamingContext}

/**
  * @author: lzm
  * @date: 2019/3/13
  * @description: Kafka直连
  * @version: 1.0
  */
object KafkaDirectWordCount {
  def main(args: Array[String]): Unit = {
    //1. 创建程序入口
    val conf: SparkConf = new SparkConf().setAppName("KafkaDirectWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Duration(500))

    //2.kafka参数
    val brokerList = "192.168.230.201:9092,192.168.230.202:9092,192.168.230.203:9092"
    //3.指定zookeeper地址 ， 更新的偏移量记录使用zk（1.存储2.监听）
    val zkQuorum = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181"
    //创建组
    val group = "g1"
    //创建主题
    val topic = "kdwc"
    val topics = Set[String](topic)
    val kafkaParams: Map[String, String] = Map[String, String](
      //设置broker的地址
      "metadata.broker.list" -> brokerList,
      //设置组id
      "group.id" -> "g1",
      //设置偏移量
      "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString
    )


    val directKafkaStream: InputDStream[(Nothing, Nothing)] = KafkaUtils.createDirectStream(ssc,kafkaParams,topics)
  }
}
