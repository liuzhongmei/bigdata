package com.itstaredu.scala05

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
case class Boy(high: Int, weight: Int)

case class Girl(high: Int, weight: Int)

object Test extends App {

  def objMatch(obj: Any): Unit = obj match {
    case Boy(x, y) => println(s"$x $y 的男孩")
    case Girl(x, y) => println(s"$x $y 的女孩")
  }

  objMatch(new Boy(188, 180))
  objMatch(new Girl(165, 100))
}
