package com.itstaredu.akka

import akka.actor.{ActorSystem, Props}

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object StudentActor {
  //创建了一个actorsystem,就等于在学校建立了一套邮件系统，可以取个名字
  private val system = ActorSystem("UniversityMessageSystem")
  //创建老师代理
  private val teachRef = system.actorOf(Props[TeacherActor])

  //发送消息给代理
  teachRef ! "老师您好"
}
