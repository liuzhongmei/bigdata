package com.itstaredu.scala02

/**
  * @author lzm
  * 可变参数
  */
object ManyParam {
  def sum(i: Int*): Int = {
    var sum = 0
    for (j <- i) {
      sum += j
    }
    sum
  }

  def setName(name: Any*): Any = {
    name
  }

  def main(args: Array[String]): Unit = {
    println(sum(1, 2, 3, 4))
    println(sum(1, 2, 3))
    println(setName("hunter",18,188))
  }
}
