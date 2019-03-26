package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:
  * @version: 1.0
  * 构造器私有
  */
class Person4 private(var name: String, age: Int) {
  private var high: Int = _

  def this(name: String, age: Int, high: Int) {
    this(name, age)
    this.high = high
  }
}
