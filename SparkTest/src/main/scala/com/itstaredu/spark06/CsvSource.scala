package com.itstaredu.spark06

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @author
  * @version
  */
object CsvSource {
  def main(args: Array[String]): Unit = {
    //1.创建sparkSession
    val sparkSession = SparkSession.builder().appName("JsonSource")
      .master("local[2]").getOrCreate()

    //2.读取json数据源
    val cread: DataFrame = sparkSession.read.csv("e:/saveCsv")

    //3.处理数据
    val rdf = cread.toDF("id","xueyuan")
    import sparkSession.implicits._
    val rs = rdf.filter($"id" <= 3)
    rs.show()

    sparkSession.stop()
  }
}
