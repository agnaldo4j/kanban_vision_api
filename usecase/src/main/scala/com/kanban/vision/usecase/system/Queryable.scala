package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Organization, KanbanSystem}
import com.kanban.vision.usecase.system.Queryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQuery}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: SystemQuery[RETURN]): Try[RETURN] = {
    query match {
      case GetAllOrganizations(kanbanSystem) =>
        Success(kanbanSystem.allOrganizations().asInstanceOf[RETURN])
      case GetOrganizationByName(name, kanbanSystem) =>
        Success(kanbanSystem.organizationByName(name).asInstanceOf[RETURN])
      case GetOrganizationById(id, kanbanSystem) =>
        Success(kanbanSystem.organizationById(id).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

object Queryable {

  trait SystemQuery[RETURN]

  case class GetOrganizationByName(name: String, kanbanSystem: KanbanSystem)
    extends SystemQuery[Option[Organization]]

  case class GetOrganizationById(id: Id, kanbanSystem: KanbanSystem) extends SystemQuery[Option[Organization]]

  case class GetAllOrganizations(kanbanSystem: KanbanSystem) extends SystemQuery[List[Organization]]

}
