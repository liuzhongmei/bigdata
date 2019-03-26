package com.itstaredu.scala06

import java.io.{BufferedReader, File, FileReader}

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
class RichFile(file: File) {
  def count(): Int = {
    var fileReader = new FileReader(file)
    var bufferedReader = new BufferedReader(fileReader)

    var sum = 0

    try {
      while (null != bufferedReader.readLine()) {
        sum += 1
      }
    } catch {
      case _: Exception => sum
    } finally {
      fileReader.close()
      bufferedReader.close()
    }
    sum
  }
}
