package com.itstaredu.scala06

import java.io.File

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object FileMain {
  implicit def file2RichFile(file: File) = new RichFile(file)

  def main(args: Array[String]): Unit = {
    val file = new File("")
    println(file.count())
  }
}
