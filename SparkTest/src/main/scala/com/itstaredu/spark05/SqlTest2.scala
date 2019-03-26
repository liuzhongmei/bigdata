package com.itstaredu.spark05

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author: lzm
  * @date: 2019/3/10
  * @description:DSL风格
  * @version: 1.0
  */
object SqlTest2 {
  def main(args: Array[String]): Unit = {
    //1. 构建SparkSession
    val session: SparkSession = SparkSession.builder().appName("SqlTest2").master("local[2]").getOrCreate()
    //2. 创建RDD
    val dataRdd: RDD[String] = session.sparkContext.textFile("e:/user.txt")
    //3. 切分数据
    val splitRdd: RDD[Array[String]] = dataRdd.map(_.split("\t"))
    //4. RDD转化为DataFrame
    val rowRdd: RDD[Row] = splitRdd.map(x => {
      val id: Int = x(0).toInt
      val name: String = x(1).toString
      val age: Int = x(2).toInt
      Row(id, name, age)
    })
    val schema: StructType = StructType(List(
      StructField("id", IntegerType),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))
    val frame: DataFrame = session.createDataFrame(rowRdd,schema)
    //5. DSL风格
    import session.implicits._
    val sql: Dataset[Row] = frame.where($"age" > 18)
    sql.show()
    //6. 关闭资源
    session.stop()
  }
}
