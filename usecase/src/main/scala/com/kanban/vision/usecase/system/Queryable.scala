package com.kanban.vision.usecase.system

import com.kanban.vision.domain.commands.SystemQueryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQueryable}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: SystemQueryable): Try[RETURN] = {
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
