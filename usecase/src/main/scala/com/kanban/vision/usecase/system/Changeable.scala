package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Organization
import com.kanban.vision.domain.SystemChangeable.{AddOrganization, DeleteOrganization, SystemCommand}
import com.kanban.vision.usecase.exceptions.OrganizationAlreadyExists

import scala.util.{Failure, Success, Try}

trait Changeable {
  def execute[RETURN](command: SystemCommand): Try[RETURN] = {
    command match {
      case AddOrganization(name, kanbanSystem) =>
        kanbanSystem.organizationByName(name) match {
          case Some(_) => Failure(new OrganizationAlreadyExists(name))
          case None => Success(kanbanSystem.addOrganization(Organization(name = name)).asInstanceOf[RETURN])
        }
      case DeleteOrganization(id, kanbanSystem) =>
        Success(kanbanSystem.removeOrganization(id).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}
