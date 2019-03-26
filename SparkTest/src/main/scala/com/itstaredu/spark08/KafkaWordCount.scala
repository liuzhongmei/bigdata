package com.itstaredu.spark08

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}

/**
  * @author: lzm
  * @date: 2019/3/12
  * @description: kafka消费数据到sparkStreaming
  * @version: 1.0
  */
object KafkaWordCount {
  def main(args: Array[String]): Unit = {
    //1. 创建StreamingContext
    val conf: SparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf,Milliseconds(2000))

    //2. 介入kafka数据源 zk地址
    val zkQuorum = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181"
    //访问组
    val groupId = "g1"
    //访问主题
    val topics = Map[String,Int]{"wc" -> 1}
    //创建Dstream
    val kafkaStream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc,zkQuorum,groupId,topics)

    //3. 处理数据
    val datas: DStream[String] = kafkaStream.map(_._2)
    val r: DStream[(String, Int)] = datas.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _)
    r.print()

    //4. 启动streaming程序
    ssc.start()
    //5. 关闭资源
    ssc.awaitTermination()
  }
}
