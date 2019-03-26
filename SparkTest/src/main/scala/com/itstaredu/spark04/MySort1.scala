package com.itstaredu.spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: lzm
  * @date: 2019/3/10
  * @description:
  * @version: 1.0
  */
object MySort {
  def main(args: Array[String]): Unit = {
    //1. spark程序入口
    val conf: SparkConf = new SparkConf().setAppName("MySort").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    //2. 定义数组
    val array: Array[String] = Array("reba,24,86", "mimi,26,100", "liya,18,103", "yichen,18,93")
    //3. 转换RDD
    val rdd1: RDD[String] = sc.parallelize(array)
    //4. 切分数据
    val rdd2: RDD[Girl] = rdd1.map(x => {
      val s: Array[String] = x.split(",")

      //拿到每个属性
      val name: String = s(0)
      val age: Int = s(1).toInt
      val weight: Int = s(2).toInt
      new Girl(name, age, weight)
    })
    val sorted: RDD[Girl] = rdd2.sortBy(x => x)
    val girls: Array[Girl] = sorted.collect()
    println(girls.toBuffer)
    sc.stop()
  }
}

class Girl(val name:String,val age:Int,val weight:Int) extends Ordered[Girl] with Serializable {
  override def compare(that: Girl): Int = {
    //年龄小的往前排
    //如果年龄相同 体重重的往前排
    //如果正数 正序 负数 倒序
    if(this.age == that.age){
      -(this.weight - that.weight)
    }else{
      this.age - that.age
    }
  }

  override def toString: String = s"名字:$name,年龄:$age,体重:$weight"
}