package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.{Id, System}
import com.kanban.vision.usecase.system.Queryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQuery}
import com.kanban.vision.usecase.system.SystemUseCase.{Fail, ManyResult, SingleResult, SystemResult}

trait Queryable {
  def execute(query: SystemQuery): SystemResult = {
    query match {
      case GetAllOrganizations(system) =>
        executeGetAllOrganizations(system)
      case GetOrganizationByName(name, system) =>
        executeGetOrganizationByName(name, system)
      case GetOrganizationById(id, system) =>
        executeGetOrganizationById(id, system)
      case _ => Fail(s"Command not found: ${query}")
    }
  }

  private def executeGetAllOrganizations(system: System): ManyResult =
    ManyResult(
      system.organizations.values.toList
    )

  private def executeGetOrganizationById(id: Id, system: System) =
    SingleResult(
      system.organizations.get(id)
    )

  private def executeGetOrganizationByName(name: String, system: System) =
    SingleResult(
      system.organizations.values.find { organization =>
        organization.name == name
      }
    )
}

object Queryable {

  trait SystemQuery

  case class GetOrganizationByName(name: String, system: System)
    extends SystemQuery

  case class GetOrganizationById(id: Id, system: System) extends SystemQuery

  case class GetAllOrganizations(system: System) extends SystemQuery

}
