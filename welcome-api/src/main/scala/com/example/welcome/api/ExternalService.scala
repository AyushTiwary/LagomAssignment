package com.example.welcome.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait ExternalService extends Service {

  def getUser: ServiceCall[NotUsed, UserData]

  override final def descriptor: Descriptor = {
    import Service._
    named("external-service").withCalls(
      restCall(Method.GET,"/posts/1", getUser)
    ).withAutoAcl(true)
  }
}

case class UserData(userId: Int,
                    id: Int,
                    title:String,
                    body: String)

object UserData{

  implicit val format:Format[UserData] = Json.format[UserData]

}
