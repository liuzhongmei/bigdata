package com.itstaredu.spark06

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author lzm
  * @version
  *          读取parquet数据源
  *          做了压缩 提高程序运行效率
  *          MR：压缩
  *          Hive：压缩
  *
  *          程序优化
  */
object ParquetDemo {

  def main(args: Array[String]): Unit = {

    //1.创建sparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("ParquetDemo")
      .master("local[2]").getOrCreate()

    import sparkSession.implicits._
    //2.读取parquet格式数据
    val prd: DataFrame = sparkSession.read.parquet("d:/savePar")

    //3.带有schema信息
//    prd.printSchema()
//    prd.show()

    //4.计算（需求：过滤出学院是java的）
    val jrs: Dataset[Row] = prd.filter($"xueyuan" === "java")

    jrs.show()

    //5.关闭资源
    sparkSession.stop()

  }

}
