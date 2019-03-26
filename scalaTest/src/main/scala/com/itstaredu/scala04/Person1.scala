package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:
  * @version: 1.0
  */
class Person1 {
  var name: String = _
  var age: Int = _
}

object TestPerson extends App {
  var person1 = new Person1
  person1.age = 18
  person1.name = "hunter"

}
