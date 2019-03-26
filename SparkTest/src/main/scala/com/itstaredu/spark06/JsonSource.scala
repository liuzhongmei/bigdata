package com.itstaredu.spark06

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author
  * @version
  */
object JsonSource {
  def main(args: Array[String]): Unit = {

    //1.创建sparkSession
    val sparkSession = SparkSession.builder().appName("JsonSource")
      .master("local[2]").getOrCreate()

    //2.读取json数据源
    val jread: DataFrame = sparkSession.read.json("e:/saveJson")

    import sparkSession.implicits._

    //3.处理数据
    val fread: Dataset[Row] = jread.filter($"xueyuan" === "bigdata")

    //4.触发action
    fread.show()

    //5.关闭资源
    sparkSession.stop()
  }
}
