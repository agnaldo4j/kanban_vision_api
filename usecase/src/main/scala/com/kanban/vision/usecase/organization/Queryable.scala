package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Kanban, PrevalentSystem}
import com.kanban.vision.usecase.organization.Queryable.{GetAllKanbansFrom, OrganizationQuery}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: OrganizationQuery[RETURN]): Try[RETURN] = {
    query match {
      case GetAllKanbansFrom(organizationId, prevalentSystem) =>
        Success(prevalentSystem.allKanbans(organizationId).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

object Queryable {

  trait OrganizationQuery[RETURN]

  case class GetAllKanbansFrom(
                                organizationId: Id,
                                prevalentSystem: PrevalentSystem
                              ) extends OrganizationQuery[List[Kanban]]

}
