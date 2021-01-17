package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Organization, PrevalentSystem}
import com.kanban.vision.usecase.system.Queryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQuery}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: SystemQuery[RETURN]): Try[RETURN] = {
    query match {
      case GetAllOrganizations(prevalentSystem) =>
        Success(prevalentSystem.allOrganizations().asInstanceOf[RETURN])
      case GetOrganizationByName(name, prevalentSystem) =>
        Success(prevalentSystem.organizationByName(name).asInstanceOf[RETURN])
      case GetOrganizationById(id, prevalentSystem) =>
        Success(prevalentSystem.organizationById(id).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

object Queryable {

  trait SystemQuery[RETURN]

  case class GetOrganizationByName(name: String, prevalentSystem: PrevalentSystem)
    extends SystemQuery[Option[Organization]]

  case class GetOrganizationById(id: Id, prevalentSystem: PrevalentSystem) extends SystemQuery[Option[Organization]]

  case class GetAllOrganizations(prevalentSystem: PrevalentSystem) extends SystemQuery[List[Organization]]

}
