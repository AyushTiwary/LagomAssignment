package com.example.welcome.impl

import akka.actor.Actor
import com.example.welcome.api.ExternalService
import play.api.Logger

class UserActor(externalService: ExternalService) extends Actor {

  override def receive: Receive = {
    case UnManagedServiceCall =>
      externalService.getUser.invoke().map(userDetails =>Logger.info(userDetails.toString))
  }

}
