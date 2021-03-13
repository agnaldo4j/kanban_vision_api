package com.kanban.vision.rest

import cats.effect.IO
import com.kanban.vision.domain.Organization
import com.kanban.vision.eventbus.EventBusCommand.{AddOrganizationOnSystem, Command}
import com.kanban.vision.eventbus.EventBus
import com.twitter.finagle.http.cookie.SameSite
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.{Cookie, Request, Response, Status}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._

object Main extends App with Endpoint.Module[IO] {

  val auth: Endpoint.Compiled[IO] => Endpoint.Compiled[IO] = compiled => {
    Endpoint.Compiled[IO] {
      case req if req.getParam("user", "teste") != "teste" =>
        compiled(req)
      case req if req.uri == "/v2/hello" =>
        compiled(req)
      case _ =>
        IO.pure(Trace.empty -> Right(Response(Status.Unauthorized)))
    }
  }

  val api: Endpoint[IO, Organization] = get("hello") {
    val cookie = new Cookie(
      name = "token",
      value = "teste",
      httpOnly = true,
      sameSite = SameSite.Lax
    )

    Ok(
      Organization(name = "Teste")
    ).withCookie(cookie)
  }

  val apiV2: Endpoint[IO, List[AddOrganizationOnSystem]] = get("v2" :: path[String]) {
    title: String =>
      val result = List.empty[Command]
      Ok(
        result.map { t =>
          t match {
            case AddOrganizationOnSystem(name) => AddOrganizationOnSystem(name = name)
            case _ => AddOrganizationOnSystem(name = "Undefined")
          }
        }
      )
  }

  val apiV3: Endpoint[IO, Organization] =
    get("v3" :: path[String] :: path[Int]) { (title: String, age: Int) =>
      Ok(
        Organization(name = title)
      )
    }

  val policy: Cors.Policy = Cors.Policy(
    allowsOrigin = _ => Some("https://kanban-vision.com.br"),
    allowsMethods = _ => Some(Seq("GET", "POST")),
    allowsHeaders = _ => Some(Seq("Accept")),
    supportsCredentials = true
  )

  val eventBus = EventBus()
  val filters = Function.chain(Seq(auth))
  val endpoints = Bootstrap.serve[Application.Json](
    RestApiOrganization.addOrganization(eventBus) :+:
      RestApiOrganization.getOrganization(eventBus) :+:
      apiV2 :+:
      api :+:
      apiV3
  ).compile
  val compiled = filters(endpoints)
  val service = Endpoint.toService(compiled)

  val corsService: Service[Request, Response] =
    new Cors.HttpFilter(policy).andThen(service)

  Await.ready(Http.server.serve("0.0.0.0:8080", corsService))
}
