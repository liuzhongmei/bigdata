package com.itstaredu.spark03

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/9
  * @description:
  * @version: 1.0
  */
object JdbcRDDDemo {
  def main(args: Array[String]): Unit = {
    //spark程序入口
    val conf: SparkConf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    val connection = ()=>{
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection("jdbc:mysql://localhost:3306/urlcount?charatorEncoding=utf-8","root","123456")
    }

    val rdd: JdbcRDD[(Int, String, String, Int)] = new JdbcRDD(
      sc,
      connection,
      "SELECT * FROM url_data WHERE id >= ? AND id <= ?",
      1, 4, 2,
      r => {
        val uid = r.getInt(1)
        var xueyuan = r.getString(2)
        var url = r.getString(3)
        var count = r.getInt(4)
        (uid, xueyuan, url, count)
      }
    )
    println(rdd.collect().toBuffer)
    sc.stop()
  }
}
