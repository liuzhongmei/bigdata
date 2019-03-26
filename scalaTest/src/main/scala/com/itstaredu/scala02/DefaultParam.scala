package com.itstaredu.scala02

/**
  * 默认参数
  */
object DefaultParam {
  /**
    * 如果调用此方法且不给参数，要一个默认值
    *
    * @param a
    * @param b
    */
  def sum(a: Int = 2, b: Int = 7): Int = {
    a + b
  }

  def main(args: Array[String]): Unit = {
    println(sum())
  }
}
