package com.itstaredu.scala06

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object ImplicitTest {
  // 隐式参数
  // 此参数如果被implicit修饰的话，调用可以不写参数
  def sleep(implicit how: String): Unit = {
    println(how)
  }

  // 隐式转换类型
  implicit def doubleToInt(d: Double): Int = (d.toInt)

  def main(args: Array[String]): Unit = {
    // 隐式参数
    implicit val how = "头疼"
    sleep

    // 隐式转换类型
    val a:Int = 8.8
    println(doubleToInt(a))
  }
}
