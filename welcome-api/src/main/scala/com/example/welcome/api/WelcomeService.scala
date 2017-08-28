package com.example.welcome.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait WelcomeService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  def getUserInfo: ServiceCall[NotUsed, String]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("welcome")
      .withCalls(
        restCall(Method.GET, "/api/hello/:id", hello _),
        restCall(Method.GET, "/api/getuserinfo", getUserInfo)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}
