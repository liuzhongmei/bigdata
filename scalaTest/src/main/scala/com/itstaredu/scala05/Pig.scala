package com.itstaredu.scala05

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object Pig extends Animal {
  override def eat(name: String): Unit = {
    println(s"$name 在吃鸡")
  }

  override def sleep(name: String): Unit = {
    println(s"$name 在做梦")
  }

  override type T = String

  def main(args: Array[String]): Unit = {
    Pig.hello("你好")
  }
}
