package com.itstaredu.scala03

import scala.collection.mutable

object CollectDemo extends App {
  val ls = List(4,7,9,3,2,3,5,8)
  ls.sortBy(x => x)

  val sv = List(("h",3),("e",2),("l",1))
  val s = sv.sortBy(x => x._2)
  val s2 = sv.sortWith((x,y) => x._2 > y._2)
  println(s)
  println(s2)

  val g = List(("h",3),("e",3),("l",1))
  val gg = g.grouped(3)
  println(gg.toList)

  val f = List(1,3,4)
  println(f.fold(1)((x,y) => x+y))

  //Set
  val ss = mutable.HashSet(2,2,3,4)
  ss.add(5)
  ss.remove(2)
  println(ss)

  //Map
  val m = Map[String,Int]("hello" -> 2,"reba" -> 1)
  val m2 = mutable.HashMap[String,Int]()
  m2.put("hello",2)
  m2.put("reba",3)
  m2.remove("reba")
  m2.get("hello")
  m2.getOrElse("reba",18)
}
