package com.itstaredu.akka

import akka.actor.{Actor, ActorSystem, Props}

/**
  * @author: lzm
  * @date: 2019/3/6
  * @description:
  * @version: 1.0
  */
object CallMe {
  private val acFactory = ActorSystem("AcFactory")

  private val callRef = acFactory.actorOf(Props[CallMe],"CallMe")

  def main(args: Array[String]): Unit = {
    callRef ! "Hunter很帅"
  }
}

class CallMe extends Actor{
  override def receive: Receive = {
    case "Hunter很帅" => println("你是对滴")
    case "Hunter不帅" => println("你是错滴")
    case "stop" => {
      //关闭代理ActorRef
      context.stop(self)
      //关闭ActorSystem
      context.system.terminate()

    }
  }
}
