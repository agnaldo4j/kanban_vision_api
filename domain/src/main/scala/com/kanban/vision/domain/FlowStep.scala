package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID
import scala.collection.SortedSet

case class FlowStep(
                     id: Id = UUID.randomUUID().toString,
                     audit: Audit = Audit(),
                     name: String,
                     order: Int,
                     requiredAbility: Option[Ability] = None,
                     tasks: SortedSet[Task] = SortedSet.empty
                   ) extends Domain with ComparableByInteger

object FlowStep {
  def sample() = SortedSet(
    FlowStep(name = "Backlog", order = 0),
    FlowStep(name = "Product", order = 1, requiredAbility = Some(Analyst)),
    FlowStep(name = "WaitDevelopment", order = 2),
    FlowStep(name = "Development", order = 3, requiredAbility = Some(Developer)),
    FlowStep(name = "WaitQA", order = 4),
    FlowStep(name = "QA", order = 5, requiredAbility = Some(QualityAssurance)),
    FlowStep(name = "WaitDeploy", order = 6),
    FlowStep(name = "Deploy", order = 7, requiredAbility = Some(Deployer)),
    FlowStep(name = "Done", order = 8),
  )
}
