package com.itstaredu.scala02

/**
  *  偏函数
  */
object PartialFunction {

  //定义函数
  def func(str:String):Int = {
    if(str.equals("hunter")) 18 else 0
  }

  //定义偏函数
  def func1:PartialFunction[String,Int] = {
    //如果使用了片函数必须用case
    case "hunter" => 18
      //如果其它
    case _  => 0
  }

  def main(args: Array[String]): Unit = {
    println(func("hunter"))
    println(func1("hunter"))
  }
}
