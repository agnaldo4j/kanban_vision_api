package com.kanban.vision.domain

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.SortedSet

object Domain {
  type Id = String

  object System {
    def apply(): System = new System()

    def apply(initialState: Map[String, Organization]): System =
      new System(organizations = initialState)
  }

  trait Domain {
    val id: Id
    val audit: Audit
  }

  trait ComparableByInteger extends Comparable[Int] {
    val order: Int

    override def compareTo(reference: Int): Int = {
      reference match {
        case greater if (order < greater) => -1
        case less if (order > less) => 1
        case _ => 0
      }
    }
  }

  case class Audit(
                    created: LocalDateTime = LocalDateTime.now(),
                    updated: Option[LocalDateTime] = None
                  )

  case class System(
                     id: Id = UUID.randomUUID().toString,
                     audit: Audit = Audit(),
                     organizations: Map[Id, Organization] = Map.empty
                   ) extends Domain

  case class Organization(
                           id: Id = UUID.randomUUID().toString,
                           audit: Audit = Audit(),
                           name: String,
                           kanbans: Map[Id, Kanban] = Map.empty
                         ) extends Domain

  case class Kanban(
                     id: Id = UUID.randomUUID().toString,
                     audit: Audit = Audit(),
                     flow: Flow
                   ) extends Domain

  case class Flow(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   steps: SortedSet[FlowSteps]
                 ) extends Domain

  case class FlowSteps(
                        id: Id = UUID.randomUUID().toString,
                        audit: Audit = Audit(),
                        order: Int,
                        tasks: SortedSet[Task]
                      ) extends Domain with ComparableByInteger

  case class Task(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   order: Int
                 ) extends Domain with ComparableByInteger

  case class Worker(
                     id: Id = UUID.randomUUID().toString,
                     audit: Audit = Audit(),
                     order: Int
                   )

}