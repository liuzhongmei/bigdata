package com.itstaredu.spark02

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/9
  * @description: 求出每个学院 访问量第一位的网址
  *               bigdata:video(直播)
  *               java:video
  *               python:teacher
  * @version: 1.0
  */
object UrlGroupCount {
  def main(args: Array[String]): Unit = {
    //1. 创建程序入口
    val conf: SparkConf = new SparkConf().setAppName("UrlGroupCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //2. 加载数据
    val rdd1: RDD[String] = sc.textFile("e://itstar.log")

    //3. 切分数据
    val rdd2: RDD[(String, Int)] = rdd1.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1), 1)
    })
    //4. 求出总的访问量
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_ + _)

    //5.取出学院
    val rdd4: RDD[(String, String, Int)] = rdd3.map(x => {
      //拿到url
      val url: String = x._1
      val host: String = new URL(url).getHost().split("[.]")(0)
      (host, url, x._2)
    })

    //6.按照学院进行分组
    val rdd5: RDD[(String, List[(String, String, Int)])] = rdd4.groupBy(_._1).mapValues(x => {
      //倒序 取出第一个
      x.toList.sortBy(_._3).reverse.take(1)
    })
    //7. 遍历
    rdd5.foreach(x => {
      println("学院为："+x._1+",访问第一的为："+x._2)
    })

    //8. 关闭资源
    sc.stop()
  }
}
