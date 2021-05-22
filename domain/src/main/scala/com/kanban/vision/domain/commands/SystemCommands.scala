package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.KanbanSystem

object SystemChangeable {

  trait SystemCommand

  case class AddOrganization(name: String, kanbanSystem: KanbanSystem) extends SystemCommand

  case class DeleteOrganization(id: Id, kanbanSystem: KanbanSystem) extends SystemCommand

}

object SystemQueryable {

  trait SystemQueryable

  case class GetOrganizationByName(name: String, kanbanSystem: KanbanSystem) extends SystemQueryable

  case class GetOrganizationById(id: Id, kanbanSystem: KanbanSystem) extends SystemQueryable

  case class GetAllOrganizations(kanbanSystem: KanbanSystem) extends SystemQueryable

}
