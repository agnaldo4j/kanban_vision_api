package com.kanban.vision.rest

import cats.effect.IO
import com.kanban.vision.eventbus.EventBusCommand.AddOrganizationOnSystem
import com.kanban.vision.eventbus.EventBusQuery.{GetAllOrganizationsFromSystem, GetOrganizationByNameFromSystem}
import com.kanban.vision.domain.Organization
import com.kanban.vision.eventbus.EventBus
import com.kanban.vision.rest.Main.{get, path}
import com.kanban.vision.rest.presentation.Presentation._
import io.finch.Endpoint.post
import io.finch.catsEffect.jsonBody
import io.finch.{BadRequest, Endpoint, InternalServerError, Ok}
import io.finch.circe._

import scala.util.{Failure, Success}

object RestApiOrganization {
  def addOrganization(eventBus: EventBus): Endpoint[IO, AddedOrganization] = post(
    "organizations" :: jsonBody[NewOrganization]
  ) { newOrganization: NewOrganization =>
    eventBus.execute[Organization](AddOrganizationOnSystem(newOrganization.name)) match {
      case Success((_, organization)) => Ok(AddedOrganization(organization))
      case Failure(ex: Exception) => BadRequest(ex)
      case Failure(ex) => InternalServerError(new IllegalStateException(ex))
    }
  }

  def getOrganizations(eventBus: EventBus): Endpoint[IO, List[Organization]] = get("organizations") {
    eventBus.execute[List[Organization]](GetAllOrganizationsFromSystem()) match {
      case Success(result) => Ok(result)
      case Failure(_) => Ok(List.empty)
    }
  }

  def getOrganization(eventBus: EventBus): Endpoint[IO, Option[Organization]] = get("organizations" :: path[String]) {
    name: String =>
    eventBus.execute[Option[Organization]](GetOrganizationByNameFromSystem(name)) match {
      case Success(result) => Ok(result)
      case Failure(ex: Exception) => BadRequest(ex)
      case Failure(ex) => InternalServerError(new IllegalStateException(ex))
    }
  }
}
