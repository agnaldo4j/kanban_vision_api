package com.kanban.vision.usecase.system

import com.kanban.vision.domain.commands.SystemQueryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQueryable}
import com.kanban.vision.usecase.QueryPerformer

import scala.util.{Failure, Success, Try}

trait Queryable extends QueryPerformer[SystemQueryable] {
  override def query[RETURN](query: SystemQueryable): Try[RETURN] = {
    query match {
      case GetAllOrganizations(kanbanSystem) =>
        kanbanSystem.allOrganizations().asInstanceOf[Try[RETURN]]
      case GetOrganizationByName(name, kanbanSystem) =>
        kanbanSystem.organizationByName(name).asInstanceOf[Try[RETURN]]
      case GetOrganizationById(id, kanbanSystem) =>
        kanbanSystem.organizationById(id).asInstanceOf[Try[RETURN]]
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}
