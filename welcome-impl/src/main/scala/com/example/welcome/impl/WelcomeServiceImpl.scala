package com.example.welcome.impl

import akka.NotUsed
import akka.actor.{ActorRef, Props}
import com.example.welcome.api.{ExternalService, UserData, WelcomeService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class WelcomeServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends WelcomeService {

  val system = akka.actor.ActorSystem("Lagom-ActorSystem")
  val userActor: ActorRef = system.actorOf(Props(classOf[UserActor], externalService))

  system.scheduler.schedule(
    0 milliseconds,
    20 seconds,
    userActor,
    UnManagedServiceCall)

  override def hello(username: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    Future.successful(s"Welcome $username")
  }

  override def getUserInfo = ServiceCall { _ =>
    val result: Future[UserData] = externalService.getUser.invoke()
    result.map(response => response.toString)
  }

}

  case object UnManagedServiceCall

 /* override def usersTopic(): Topic[api.UserDataChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(UserEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(userEvent: EventStreamElement[UserEvent]): api.UserDataChanged= {
    userEvent.event match {
      case UserDataChanged(userId, id, title, body) => api.UserDataChanged(userId, id, title, body)
    }
  }*/
/*

sealed trait UserEvent extends AggregateEvent[UserEvent] {
  def aggregateTag = UserEvent.Tag
}

object UserEvent {
  val Tag: AggregateEventTag[UserEvent] = AggregateEventTag[UserEvent]
}

/*case class UserDataChanged(userId: Int,
                           id: Int,
                           title:String,
                           body: String) extends UserEvent

object UserDataChanged {
  implicit val format: Format[UserDataChanged] = Json.format[UserDataChanged]
}*/

object UserSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[UserData]/*,
    JsonSerializer[UserDataChanged]*/
  )
}*/
