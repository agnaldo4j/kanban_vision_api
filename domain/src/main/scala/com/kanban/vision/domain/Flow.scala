package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID
import scala.collection.SortedSet

case class Flow(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 steps: SortedSet[FlowStep] = SortedSet.empty
               ) extends Domain

object Flow {
  def simpleOne(): Flow = {
    val flowSteps = FlowStep.sample()
    new Flow(steps = flowSteps)
  }
}
