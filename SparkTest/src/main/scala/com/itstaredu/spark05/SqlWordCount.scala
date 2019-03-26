package com.itstaredu.spark05

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @author: lzm
  * @date: 2019/3/10
  * @description:
  * @version: 1.0
  */
object SqlWordCount {
  def main(args: Array[String]): Unit = {
    //1. 构建session
    val session: SparkSession = SparkSession.builder().appName("SqlWordCount").master("local[2]").getOrCreate()
    //2.加载数据 使用dataSet处理数据 dataSet是一个更加智能的rdd，默认有一列叫value,value存储的是所有数据
    val datas: Dataset[String] = session.read
      .textFile("e:/wordcount.txt")

    //3.sparksql 注册表/注册视图 rdd.flatMap
    import session.implicits._
    val word: Dataset[String] = datas.flatMap(_.split("\t"))

    //4.注册视图
    word.createTempView("wc_t")

    //5.执行sql wordcount
    val r: DataFrame = session
      .sql("select value as word,count(*) sum from wc_t group by value order by sum desc")

    r.show()
    session.stop()

  }

}
