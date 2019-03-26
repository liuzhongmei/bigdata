package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:
  * @version: 1.0
  */
private[this] class Person5(var name: String, var age: Int) {
  var hight: Int = _

  def this(name: String, age: Int, hight: Int) {
    this(name,age)
    this.hight = hight
  }

}
