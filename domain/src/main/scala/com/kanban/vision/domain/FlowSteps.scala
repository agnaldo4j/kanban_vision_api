package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID
import scala.collection.SortedSet

case class FlowSteps(
                      id: Id = UUID.randomUUID().toString,
                      audit: Audit = Audit(),
                      order: Int,
                      tasks: SortedSet[Task]
                    ) extends Domain with ComparableByInteger
