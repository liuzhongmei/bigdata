package com.itstaredu.scala05

trait Animal {
  def eat(name: String)

  def sleep(name: String): Unit = {
    println(s"$name => 在睡觉")
  }

  type T
  def hello(str: T): Unit = {
    println(str)
  }
}
