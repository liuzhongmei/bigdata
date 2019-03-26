package com.itstaredu.scala02

/**
  * 传值调用&传名调用
  */
object ZFBToPAy {
  var money = 1000;

  def eat(): Unit = {
    money = money - 50
  }

  def balance(): Int = {
    eat()
    money
  }

  def printMoney1(x:Int): Unit = {
    for(a <- 1 to 5){
      println(f"你的余额现在为：$x")
    }
  }

  def printMoney2(x: =>Int): Unit = {
    for(a <- 1 to 5){
      println(f"你的余额现在为：$x")
    }
  }

  def main(args: Array[String]): Unit = {
//    eat()
//    balance()
//    printMoney1(balance())
    printMoney2(balance())
  }

}
