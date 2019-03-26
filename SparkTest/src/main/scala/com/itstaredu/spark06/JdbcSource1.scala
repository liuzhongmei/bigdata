package com.itstaredu.spark06

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author: lzm
  * @date: 2019/3/11
  * @description:
  * @version: 1.0
  */
object JdbcSource1 {
  def main(args: Array[String]): Unit = {
    //1. sparkSQL  创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JdbcSource1").master("local[2]").getOrCreate()
    //2. 加载数据源
    import sparkSession.implicits._

    val urlData: DataFrame = sparkSession.read.format("jdbc").options(Map(
      "url" -> "jdbc:mysql://localhost:3306/urlcount",
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> "url_data",
      "user" -> "root",
      "password" -> "123456"
    )).load()
    val r: Dataset[Row] = urlData.filter($"id" > 2)
    val rs: DataFrame = r.select($"xueyuan",$"url",$"count")
    //写入以text格式
//    rs.write.text("e:/saveText")

    //写入以json格式
    rs.write.json("e:/saveJson")

    //写入以csv格式
//    rs.write.csv("e:/saveCvs")

//    rs.write.parquet("e:/saveParquet")
    rs.show()
    sparkSession.stop()
  }
}
