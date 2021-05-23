package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

object Project {
  def zero(): Project = Project(order = 0)
  
  def one(): Project = Project(order = 1, tasks = Task.toProjectOne())
}

case class Project(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int,
                 tasks: Map[Id, Task] = Map.empty
               ) extends Domain with ComparableByInteger
