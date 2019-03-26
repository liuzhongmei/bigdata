package com.itstaredu.scala06

import java.io.File

import scala.io.Source

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object ReadImplicit {
  implicit class FileRead(file:File){
    def read:String = Source.fromFile(file).mkString
  }

  def main(args: Array[String]): Unit = {
    val file = new File("")
    println(file.read())
  }
}
