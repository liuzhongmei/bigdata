package com.itstaredu.spark05

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * @author: lzm
  * @date: 2019/3/10
  * @description:SQL风格
  * @version: 1.0
  */
object SqlTest {
  def main(args: Array[String]): Unit = {
    //1. 构建SparkSession
    val session: SparkSession = SparkSession.builder().appName("SqlTest").master("local[2]").getOrCreate()

    //2. 创建RDD
    val dataRdd: RDD[String] = session.sparkContext.textFile("e:/user.txt")
    //3. 切分数据
    val splitRdd: RDD[Array[String]] = dataRdd.map(_.split("\t"))
    //4. 封装数据
    val rowRdd: RDD[Row] = splitRdd.map(x => {
      val id: Int = x(0).toInt
      val name: String = x(1).toString
      val age: Int = x(2).toInt
      Row(id, name, age)
    })

    //5. 创建schema
    val schema: StructType = StructType(List(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))

    //6. 创建DataFrame
    val frame: DataFrame = session.createDataFrame(rowRdd,schema)
    //7. 注册表
    frame.registerTempTable("user_t")
    //8. 写sql
    val userSql: DataFrame = session.sql("select * from user_t")
    //9. 查看结果
    userSql.show()
    //10. 释放资源
    session.stop()
  }
}
