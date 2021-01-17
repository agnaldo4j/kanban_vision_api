package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Kanban, PrevalentSystem}
import com.kanban.vision.usecase.organization.Changeable.{AddSimpleKanban, OrganizationCommand}

import scala.util.{Failure, Try}

trait Changeable {
  def execute[RETURN](command: OrganizationCommand[RETURN]): Try[RETURN] = {
    command match {
      case AddSimpleKanban(organizationId, prevalentSystem) =>
        prevalentSystem.addKanbanOn(organizationId, Kanban.simpleOne()).asInstanceOf
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}

object Changeable {

  trait OrganizationCommand[RETURN]

  case class AddSimpleKanban(
                              organizationId: Id,
                              prevalentSystem: PrevalentSystem
                            ) extends OrganizationCommand[PrevalentSystem]

}
