package com.itstaredu.scala04

trait Animal {
  def eat(name:String)

  def sleep(name:String):Unit={
    println(s"$name 在睡觉")
  }

}
