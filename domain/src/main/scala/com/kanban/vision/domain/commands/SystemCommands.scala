package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{
  KanbanSystem, Organization, KanbanSystemChanged
}

import scala.util.{Try, Success, Failure}

object SystemChangeable {

  trait SystemCommand[RETURN] {
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }

  case class AddOrganization(name: String, kanbanSystem: KanbanSystem) extends SystemCommand[Organization] {
    override def execute(): Try[KanbanSystemChanged[Organization]] = kanbanSystem.organizationByName(name) match {
      case Success(Some(_)) => Failure(IllegalStateException("Organization already exists with name: $name"))
      case Success(None) => kanbanSystem.addOrganization(Organization(name = name))
      case Failure(ex) => Failure(ex)
    }
  }

  case class DeleteOrganization(id: Id, kanbanSystem: KanbanSystem) extends SystemCommand[Option[Organization]] {
    override def execute(): Try[KanbanSystemChanged[Option[Organization]]] = kanbanSystem.removeOrganization(id)
  }
}

object SystemQueryable {

  trait SystemQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetOrganizationByName(name: String, kanbanSystem: KanbanSystem) extends SystemQuery[Option[Organization]] {
    override def execute(): Try[Option[Organization]] = kanbanSystem.organizationByName(name)
  }

  case class GetOrganizationById(id: Id, kanbanSystem: KanbanSystem) extends SystemQuery[Option[Organization]] {
    override def execute(): Try[Option[Organization]] = kanbanSystem.organizationById(id)
  }

  case class GetAllOrganizations(kanbanSystem: KanbanSystem) extends SystemQuery[List[Organization]] {
    override def execute(): Try[List[Organization]] = kanbanSystem.allOrganizations()
  }
}
