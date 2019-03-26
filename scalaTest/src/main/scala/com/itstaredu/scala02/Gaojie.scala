package com.itstaredu.scala02

/**
  * 高阶函数
  */
object Gaojie {
  def getPerson(h: (String, Int) => String, name: String, age: Int): String = {
    h(name, age)
  }

  def person(name: String, age: Int): String = name + "今年" + age

  def main(args: Array[String]): Unit = {
    println(getPerson(person, "hunter", 18))
  }
}
