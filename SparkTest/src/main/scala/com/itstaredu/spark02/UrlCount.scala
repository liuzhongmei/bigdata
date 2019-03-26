package com.itstaredu.spark02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/9
  * @description: 计算网页访问量前三名
  * @version: 1.0
  */
object UrlCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    //spark程序入口
    val sc: SparkContext = new SparkContext(conf)
    //1. 载入数据
    val rdd: RDD[String] = sc.textFile("E:\\itstar.log")
    //2. 对数据进行计算
    val rdd1: RDD[(String, Int)] = rdd.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1), 1)
    })
    //3. 将相同的网址进行累加求和  网页,201
    val rdd2: RDD[(String, Int)] = rdd1.reduceByKey((x, y) => x + y)
    //4. 排序 取出前三
    val rdd3: Array[(String, Int)] = rdd2.sortBy(_._2, false).take(3)

    //5. 遍历打印
    rdd3.foreach(x => {
      println("网址为:" + x._1 + " 访问量为" + x._2)
    })

    sc.stop()
  }
}
