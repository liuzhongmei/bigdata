package com.itstaredu.spark06

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author: lzm
  * @date: 2019/3/11
  * @description: mysql作为数据源
  * @version: 1.0
  */
object JdbcSource {
  def main(args: Array[String]): Unit = {
    //1. sparkSQL  创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JdbcSource").master("local[2]").getOrCreate()
    //2. 加载数据源

    val urlData: DataFrame = sparkSession.read.format("jdbc").options(Map(
      "url" -> "jdbc:mysql://localhost:3306/urlcount",
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> "url_data",
      "user" -> "root",
      "password" -> "123456"
    )).load()

    //3. 获取信息
    // root
    // |-- id: integer (nullable = false)
    // |-- xueyuan: string (nullable = true)
    // |-- url: string (nullable = true)
    // |-- count: integer (nullable = true)
//    urlData.printSchema()
//    urlData.show()

    val fData: Dataset[Row] = urlData.filter(x => {
      x.getAs[Int](0) > 2
    })
    fData.show()

    sparkSession.stop()
  }
}
