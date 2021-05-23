package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

sealed abstract class Service

case object Expedite extends Service

case object FixedDate extends Service

case object Standard extends Service

case object Intangible extends Service

object Task {

  def toProjectOne(): Map[Id, Task] = Map(
    buildTask(order = 0),
    buildTask(order = 1),
    buildTask(order = 2)
  )

  private def buildTask(
                         id: Id = UUID.randomUUID().toString,
                         order: Int
                       ): (Id, Task) = (id, Task(id = id, order = order))
}

case class Task(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int,
                 serviceClass: Service = Standard
               ) extends Domain with ComparableByInteger
