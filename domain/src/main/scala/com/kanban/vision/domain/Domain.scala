package com.kanban.vision.domain

import java.time.LocalDateTime
import java.util.UUID

object Domain {
  type Id = String

  object System {
    def apply(): System = new System()
    def apply(initialState: Map[String, Organization]): System =
      new System(organizations = initialState)
  }

  trait Domain {
    val id: Id
    val created: LocalDateTime
    val updated: Option[LocalDateTime]
  }

  case class System(
                     id: Id = UUID.randomUUID().toString,
                     created: LocalDateTime = LocalDateTime.now(),
                     updated: Option[LocalDateTime] = None,
                     organizations: Map[Id, Organization] = Map.empty
                   ) extends Domain

  case class Organization(
                           id: Id = UUID.randomUUID().toString,
                           created: LocalDateTime = LocalDateTime.now(),
                           updated: Option[LocalDateTime] = None,
                           name: String,
                           values: List[Value] = List.empty,
                           people: Map[Id, Person] = Map.empty
                         ) extends Domain

  case class Value(
                    id: Id = UUID.randomUUID().toString,
                    created: LocalDateTime = LocalDateTime.now(),
                    updated: Option[LocalDateTime] = None,
                    name: String
                  ) extends Domain

  case class Person(
                     id: Id = UUID.randomUUID().toString,
                     created: LocalDateTime = LocalDateTime.now(),
                     updated: Option[LocalDateTime] = None,
                     name: String,
                     givenFeedback: Set[Feedback] = Set.empty,
                     receivedFeedback: Set[Feedback] = Set.empty
                   ) extends Domain

  case class Feedback(
                       id: Id = UUID.randomUUID().toString,
                       created: LocalDateTime = LocalDateTime.now(),
                       updated: Option[LocalDateTime] = None,
                       values: List[Value],
                       from: Person,
                       to: Person,
                       date: LocalDateTime,
                       description: String
                     ) extends Domain

}
