package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:辅助构造器
  * @version: 1.0
  */
class Person3(var name:String,age:Int) {
  var hight:Int = _
  var weight:Int = _

  //定义辅助构造器
  def this(name:String,age:Int,hight:Int){
    this(name,age)
    this.hight = hight
  }

}
object Test2 extends App {
  /*
    如果主构造器中成员变量属性没有被val var修饰的话，该属性不能被访问
    相当于java中没有对外提供get方法

    如果成员属性使用var修饰的话，相当于java中对外提供了get方式和set方式
    如果成员属性使用val修饰的话，相当于java中对外只提供了get方式
   */
  val p = new Person3("Hunter",18)
  println(p.name)
}