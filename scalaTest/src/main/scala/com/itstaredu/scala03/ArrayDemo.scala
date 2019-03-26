package com.itstaredu.scala03

import scala.collection.mutable.ArrayBuffer

object ArrayDemo extends App {
//  val array1 = new Array[String](3)
//  val array2 = Array(0,1,2,3,4)
//
//  //map方法
//  var array3 = array2.map(x => 10 * x)
//  array3.foreach(x => {
//    val w = x + 5;
//    println(w)
//  })
//  println(array3.toBuffer)


  val arr = Array("hello hunter henshuai","hello reba henmei")
  //flatten
  val arr2 = arr.map(_.split(" ")).flatten
  println(arr2.toBuffer)
  //flatMap
  val arr3 = arr.flatMap(_.split(" "))
  println(arr2.toBuffer)
  //foreach
  arr3.foreach(x => println(x))
  //groupBy
  // Map[String,Array[String]]=Map(
  // henshuai -> Array(henshuai),
  // hunter -> Array(hunter, hunter),
  // henmei -> Array(henmei),
  // hello -> Array(hello, hello)
  // )
  val arr4 = arr3.groupBy(x => x)
  println(arr4.toBuffer)
  //单词计数
  val arr5 = arr4.map(x => (x._1, x._2.length))
  println(arr5.toBuffer)
  //排序
  val arr6 = arr5.toList.sortBy(x => -x._2)
  println(arr6.toBuffer)

  val l = ArrayBuffer(1, 2, 3)
  l += 20
  l(3) = 4
  l += 30
  println(l)
}
