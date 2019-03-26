package com.itstaredu.scala04

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description: 在scala中object是一个单例对象
  *              在scala中object定义的成员变量和方法都是静态的
  *              可以通过 类名. 来进行调用
  * @version: 1.0
  */
object ScalaMain {
  def main(args: Array[String]): Unit = {
    ScalaTest.sleep("hunter 在睡觉")
  }

}
