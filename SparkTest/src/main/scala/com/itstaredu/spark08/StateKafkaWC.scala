package com.itstaredu.spark08

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}

/**
  * @author
  * @version
  */
object StateKafkaWC {

  //保持历史状态 wc 单词，次数 聚合的key,第一个类型就是单词。第二个类型，在每一个分区中出现的次数
  //累加的结果
  val updateFunc = (iter:Iterator[(String,Seq[Int],Option[Int])]) => {
    //总的次数=当前出现的次数+以前返回的结果
    iter.map(t => (t._1,t._2.sum + t._3.getOrElse(0)))
  }


  def main(args: Array[String]): Unit = {
    //1.创建程序入口
    val conf = new SparkConf().setAppName("StateKafkaWC").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Milliseconds(2000))

    //2.接入kafka数据源
    val zkQuorum = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181"
    //组
    val groupId = "g1"
    //主题
    val topic = Map[String,Int]("wc" -> 1)
    val kafkaStream = KafkaUtils.createStream(ssc,zkQuorum,groupId,topic)

    //3.处理数据
    val datas = kafkaStream.map(_._2)

    //4.加入历史数据计算
    val r = datas.flatMap(_.split(" ")).map((_, 1))
      //参数1：自定义业务函数 参数2：分区器设置  参数3：是否使用
      .updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)

    //5.打印
    r.print()

    //6.启动程序
    ssc.start()

    //7.关闭资源
    ssc.awaitTermination()
  }
}
