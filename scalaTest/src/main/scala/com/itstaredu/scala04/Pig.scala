package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:
  * @version: 1.0
  */
object Pig extends Animal with Running {
  def eat(name: String): Unit = {
    println(s"$name 在吃🐖")
  }

  override def sleep(name: String): Unit = {
    println(s"$name 长膘")
  }

  override def bow(name: String): Unit = {
    println(s"$name ")
  }
}
