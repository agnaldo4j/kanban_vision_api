package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Kanban, KanbanSystem}
import com.kanban.vision.usecase.organization.Queryable.{GetAllKanbansFrom, OrganizationQuery}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: OrganizationQuery[RETURN]): Try[RETURN] = {
    query match {
      case GetAllKanbansFrom(organizationId, kanbanSystem) =>
        Success(kanbanSystem.allKanbans(organizationId).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

object Queryable {

  trait OrganizationQuery[RETURN]

  case class GetAllKanbansFrom(
                                organizationId: Id,
                                kanbanSystem: KanbanSystem
                              ) extends OrganizationQuery[List[Kanban]]

}
