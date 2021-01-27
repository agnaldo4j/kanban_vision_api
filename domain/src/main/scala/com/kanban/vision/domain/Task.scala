package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

sealed abstract class Service

case object Expedite extends Service

case object FixedDate extends Service

case object Standard extends Service

case object Intangible extends Service

case class Task(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int,
                 serviceClass: Service = Standard
               ) extends Domain with ComparableByInteger
