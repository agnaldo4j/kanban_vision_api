package com.kanban.vision.rest

import cats.effect.IO
import com.kanban.vision.domain.StorableEvent.{AddOrganization, StorableEvent}
import com.kanban.vision.rest.Main.{get, path}
import io.finch.Endpoint.post
import io.finch.{Endpoint, Ok}

object RestApiOrganization {
  def addOrganization(): Endpoint[IO, List[AddOrganization]] = post("organizations") {
      val result = List.empty[StorableEvent]
      Ok(
        result.map { t =>
          t match {
            case AddOrganization(name) => AddOrganization(name = name)
            case _                     => AddOrganization(name = "Undefined")
          }
        }
      )
  }

  def getOrganization(): Endpoint[IO, List[AddOrganization]] = get("organizations" :: path[String]) {
    organizationId: String =>
    val result = List.empty[StorableEvent]
    Ok(
      result.map { t =>
        t match {
          case AddOrganization(name) => AddOrganization(name = name)
          case _                     => AddOrganization(name = "Undefined")
        }
      }
    )
  }
}
