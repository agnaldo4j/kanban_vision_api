package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID
import scala.collection.SortedSet

case class Flow(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 steps: SortedSet[FlowSteps] = SortedSet.empty
               ) extends Domain
