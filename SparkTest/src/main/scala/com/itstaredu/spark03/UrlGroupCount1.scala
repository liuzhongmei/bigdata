package com.itstaredu.spark03

import java.net.URL
import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/9
  * @description:求出每个学院 访问量第一位的网址
  *                     bigdata:video(直播)
  *                     java:video
  *                     python:teacher
  * @version: 1.0
  */
object UrlGroupCount1 {
  def main(args: Array[String]): Unit = {
    //获取spark程序入口
    val conf: SparkConf = new SparkConf().setAppName("UrlGroupCount1").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    //1. 加载文件
    val rdd1: RDD[String] = sc.textFile("e://itstar.log")
    //2. 切分
    val rdd2: RDD[(String, Int)] = rdd1.map(x => {
      val strings: Array[String] = x.split("\t")
      (strings(1), 1)
    })
    //3. 聚合
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_ + _)
    //4. 取出学院
    val rdd4: RDD[(String, String, Int)] = rdd3.map(x => {
      val url: String = x._1
      //获取主机
      val host: String = new URL(url).getHost
      val xHost: String = host.split("[.]")(0)
      (xHost,url, x._2)
    })
    //5. 根据学院分组
    val rdd5: RDD[(String, List[(String, String, Int)])] = rdd4.groupBy(_._1).mapValues(x => {
      x.toList.sortBy(_._3).reverse.take(1)
    })
    //6. 把结果保存到mysql中
    rdd5.foreach(x =>{
      //把spark结果插入到mysql中
      val connection: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/urlcount?charatorEncoding=utf-8","root","123456")
      val sql = "INSERT INTO url_data (xueyuan,url,count) VALUES (?,?,?)"
      val statement: PreparedStatement = connection.prepareStatement(sql)
      val value: List[(String, String, Int)] = x._2
      statement.setString(1,x._1.toString)
      statement.setString(2,x._2(0)._2.toString)
      statement.setInt(3,value(0)._3)
      statement.executeUpdate()
      statement.close()
      connection.close()
    })
  }
}
