package com.itstaredu.scala01

object ScalaDemo1 {
  def main(args: Array[String]): Unit = {
    println(m1(1, 2))
    println(m2(3, 67))
    println(m3)
    m4
    h1("hello hangzhou",3,4)
  }

  def m1(a: Int, b: Int): Int = {
    a + b
  }

  def m2(a: Int, b: Int): Int = a * b

  def m3 : Range.Inclusive = 1.to(10)

  def m4 = {
    for (i <- 1 to 5; j <- 1 to 10 if i != j)
      println(i * 10 + j)
  }

  val h1 = (a:String,b:Int,c:Int) => {
    println(a)
    println(b*c)
  }

}
