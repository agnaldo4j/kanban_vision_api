package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

object SystemChangeable {

  trait SystemCommand

  case class AddOrganization(name: String, kanbanSystem: KanbanSystem) extends SystemCommand

  case class DeleteOrganization(id: Id, kanbanSystem: KanbanSystem) extends SystemCommand

}

object SystemQueryable {

  trait SystemQuery

  case class GetOrganizationByName(name: String, kanbanSystem: KanbanSystem) extends SystemQuery

  case class GetOrganizationById(id: Id, kanbanSystem: KanbanSystem) extends SystemQuery

  case class GetAllOrganizations(kanbanSystem: KanbanSystem) extends SystemQuery

}

