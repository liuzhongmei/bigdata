package com.itstaredu.scala04

import scala.util.matching.Regex.Match

/**
  * @author: lzm
  * @date: 2019/3/5
  * @description:
  * @version: 1.0
  */
object MatchTest {
  def main(args: Array[String]): Unit = {
    def strMatch(str:String) = str match{
        case "hunter" => println("很帅")
        case "reba" => println("很美")
        case _ => println("其他")
    }

    def arrayMatch(arr:Any) = arr match{
      case Array(1) => println("只有一个元素的数组")
    }
    strMatch("hunter")
  }
}
