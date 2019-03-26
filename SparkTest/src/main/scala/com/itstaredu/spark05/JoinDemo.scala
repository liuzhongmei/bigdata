package com.itstaredu.spark05

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @author
  * @version
  *          SQL方式
  */
object JoinDemo {
  def main(args: Array[String]): Unit = {
    //1.创建sparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JoinDemo")
      .master("local[2]").getOrCreate()

    import sparkSession.implicits._

    //2.直接创建dataSet
    val datas1: Dataset[String] = sparkSession
      .createDataset(List("1 hunter 18","2 reba 22","3 mimi 16"))

    //3.整理数据
    val dataDS1: Dataset[(Int, String, Int)] = datas1.map(x => {
      val fields: Array[String] = x.split(" ")
      val id = fields(0).toInt
      val name = fields(1).toString
      val age = fields(2).toInt

      //元祖输出
      (id, name, age)
    })

    val dataDF1: DataFrame = dataDS1.toDF("id","name","age")

    //2.创建第二份数据
    val datas2: Dataset[String] = sparkSession
      .createDataset(List("18 young","22 old"))

    val dataDS2: Dataset[(Int, String)] = datas2.map(x => {
      val fields: Array[String] = x.split(" ")
      val age = fields(0).toInt
      val desc = fields(1).toString

      //元祖输出
      (age, desc)
    })
    //3.转化为dataFrame
    val dataDF2: DataFrame = dataDS2.toDF("dage","desc")

    //4.注册视图
    dataDF1.createTempView("d1_t")
    dataDF2.createTempView("d2_t")

    //5.写sql(join)
    val r = sparkSession.sql("select name,desc from d1_t join d2_t on age = dage")

    //6.触发任务
    r.show()

    //7.关闭资源
    sparkSession.stop()

  }
}
