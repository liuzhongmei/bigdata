package com.itstaredu.spark01

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/7
  * @description: spark-WordCount本地模式测试
  * @version: 1.0
  */
object SparkWc {
  def main(args: Array[String]): Unit = {
    //2.设置参数 setAppName设计程序名 setMaster本地测试设置线程数 *多个
    val conf: SparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    //1.创建spark执行程序的入口
    val sc: SparkContext = new SparkContext(conf)
    //3.加载数据 并且处理
    sc.textFile(args(0))
      .flatMap(_.split("\t"))
      .map((_, 1))
      .reduceByKey(_ + _)
//      .sortBy(_._2, false)
      .saveAsTextFile(args(1))

    //关闭资源
    sc.stop()
  }

}
