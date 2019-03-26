package com.itstaredu.spark07

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Milliseconds,StreamingContext}

/**
  * @author: lzm
  * @date: 2019/3/11
  * @description: rdd:创建程序的入口 SparkContext
  *              dataFrame:创建程序的入口 SparkSession
  * @version: 1.0
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    //2. 创建StreamingContext
    val ssc: StreamingContext = new StreamingContext(sc,Milliseconds(2000))
    //3. DStream来表示来自TCP源的流数据
    val datas: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.230.204",7788)
    //4. 进行计算
    val rd: DStream[(String, Int)] = datas.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    //5. 打印结果
    rd.print()
    //6. 需要启动
    ssc.start()
    ssc.awaitTermination()
  }
}
