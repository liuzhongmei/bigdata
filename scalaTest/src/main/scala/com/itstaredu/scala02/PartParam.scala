package com.itstaredu.scala02

import java.util.Date

object PartParam extends App{
  def log(date: Date, message: String): Unit = {
    println(s"$date,$message")
  }

  val date = new Date()
  val logMessage = log(date,_:String)

  log(date,"helloHUnter")
  logMessage("reba");

}
