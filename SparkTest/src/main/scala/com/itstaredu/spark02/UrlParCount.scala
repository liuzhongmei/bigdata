package com.itstaredu.spark02

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @author: lzm
  * @date: 2019/3/9
  * @description:需求 ：加入自定义分区
  *                 按照学院分区，相同的学院分为一个结果文件
  * @version: 1.0
  */
object UrlParCount {
  def main(args: Array[String]): Unit = {
    //获取spark程序入口
    val conf: SparkConf = new SparkConf().setAppName("UrlParCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //1. 加载数据
    val rdd1: RDD[String] = sc.textFile("e://itstar.log")

    //2. 读取数据
    val rdd2: RDD[(String, Int)] = rdd1.map(x => {
      val s: Array[String] = x.split("\t")
      (s(1), 1)
    })
    //3. 聚合
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_ + _)

    //4. 自定义格式
    val rdd4: RDD[(String, (String, Int))] = rdd3.map(x => {
      val url: String = x._1
      val host: String = new URL(url).getHost
      val xhost: String = host.split("[.]")(0)
      //元组输出
      (xhost, (url, x._2))
    })

    //5. 加入自定义分区
    val xueyuan: Array[String] = rdd4.map(_._1).distinct().collect()
    val partition: XueyuanPartition = new XueyuanPartition(xueyuan)

    //6. 加入分区规则
    val rdd5: RDD[(String, (String, Int))] = rdd4.partitionBy(partition).mapPartitions(x => {
      x.toList.sortBy(_._2._2).reverse.iterator
    })
    //7. 结果存储
    rdd5.saveAsTextFile("e://partout")

    //8. 关闭资源
    sc.stop()
  }
}

class XueyuanPartition(xueyuan: Array[String]) extends Partitioner {
  //总的分区个数
  override def numPartitions: Int = xueyuan.length

  //自定义规则 学院 分区号
  private val rules: mutable.HashMap[String, Int] = new mutable.HashMap[String, Int]()
  var numbers: Int = 0

  for (i <- xueyuan) {
    rules.put(i, numbers)
    numbers += 1
  }

  //拿到分区
  override def getPartition(key: Any): Int = {
    rules.getOrElse(key.toString, 0)
  }
}
