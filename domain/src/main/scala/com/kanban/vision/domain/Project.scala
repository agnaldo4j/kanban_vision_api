package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

object Project {
  def defaultProjectsToSimpleSimulation(): Map[Id, Project] = Map(
    (zero.id -> zero), (one.id -> one)
  )

  val zero: Project = Project(order = 0)
  
  val one: Project = Project(order = 1, tasks = Task.toProjectOne())
}

case class Project(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int,
                 tasks: Map[Id, Task] = Map.empty
               ) extends Domain with ComparableByInteger
