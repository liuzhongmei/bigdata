package com.itstaredu.akka

import akka.actor.Actor

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
class TeacherActor extends Actor {
  override def receive: Receive = {
    case "老师您好" => {
      println("已收到消息")
      //关闭代理ActorRef
      context.stop(self)
      //关闭ActorSystem
      context.system.terminate()    }
  }
}
