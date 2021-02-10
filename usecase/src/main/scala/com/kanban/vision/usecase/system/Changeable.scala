package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Organization
import com.kanban.vision.domain.SystemChangeable.{AddOrganization, DeleteOrganization, SystemCommand}

import scala.util.{Failure, Success, Try}

trait Changeable {
  def execute[RETURN](command: SystemCommand): Try[RETURN] = {
    command match {
      case AddOrganization(name, kanbanSystem) =>
        Success(kanbanSystem.addOrganization(Organization(name = name)).asInstanceOf[RETURN])
      case DeleteOrganization(id, kanbanSystem) =>
        Success(kanbanSystem.removeOrganization(id).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}
