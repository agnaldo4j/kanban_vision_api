package com.kanban.vision.rest

import cats.effect.IO
import com.kanban.vision.domain.StorableEvent.AddOrganizationOnSystem
import com.kanban.vision.eventbus.EventBus
import com.kanban.vision.rest.Main.path
import io.finch.Endpoint.post
import io.finch.{BadRequest, Endpoint, Ok}

import scala.util.{Failure, Success}

object RestApiOrganization {
  def addOrganization(eventBus: EventBus): Endpoint[IO, String] = post("organizations") {

    val result = eventBus.execute(AddOrganizationOnSystem("name"))
    result match {
      case Success(_) => Ok("Ok")
      case Failure(ex: Exception) => BadRequest(ex)
    }
  }
}
