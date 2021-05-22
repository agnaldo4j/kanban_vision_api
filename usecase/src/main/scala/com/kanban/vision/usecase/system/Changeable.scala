package com.kanban.vision.usecase.system

import com.kanban.vision.domain.{KanbanSystemChanged, Organization}
import com.kanban.vision.domain.commands.SystemChangeable.{AddOrganization, DeleteOrganization, SystemCommand}
import com.kanban.vision.usecase.exceptions.OrganizationAlreadyExists

import scala.util.{Failure, Success, Try}

trait Changeable {
  def execute[RETURN](command: SystemCommand): Try[KanbanSystemChanged[RETURN]] = {
    command match {
      case AddOrganization(name, kanbanSystem) =>
        kanbanSystem.organizationByName(name) match {
          case Some(_) => Failure(new OrganizationAlreadyExists(name))
          case None => {
            kanbanSystem
              .addOrganization(Organization(name = name))
              .asInstanceOf[Try[KanbanSystemChanged[RETURN]]]
          }
        }
      case DeleteOrganization(id, kanbanSystem) => {
        kanbanSystem
          .removeOrganization(id)
          .asInstanceOf[Try[KanbanSystemChanged[RETURN]]]
      }
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}
