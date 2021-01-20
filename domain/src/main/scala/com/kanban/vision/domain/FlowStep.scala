package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID
import scala.collection.SortedSet

case class FlowStep(
                     id: Id = UUID.randomUUID().toString,
                     audit: Audit = Audit(),
                     name: String,
                     order: Int,
                     tasks: SortedSet[Task] = SortedSet.empty
                   ) extends Domain with ComparableByInteger

object FlowStep {
  def sample() = SortedSet(
    FlowStep(name = "Backlog", order = 0),
    FlowStep(name = "Product", order = 1),
    FlowStep(name = "WaitDevelopment", order = 2),
    FlowStep(name = "Development", order = 3),
    FlowStep(name = "WaitQA", order = 4),
    FlowStep(name = "QA", order = 5),
    FlowStep(name = "Done", order = 6),
  )
}
