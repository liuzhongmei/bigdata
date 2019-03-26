package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description: 伴生对象
  * @version: 1.0
  */
class Person6(private var name: String, age: Int) {
  private var hight: Int = _

  def this(name: String, age: Int, hight: Int) {
    this(name, age)
    this.hight = hight
  }
}
object Person6 extends App{
  val p = new Person6("hunter",18,188)
  println(p.name)
}
