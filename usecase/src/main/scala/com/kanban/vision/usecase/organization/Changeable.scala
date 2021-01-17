package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Kanban, PrevalentSystem}
import com.kanban.vision.usecase.organization.Changeable.{AddSimpleKanban, OrganizationCommand}

import scala.util.{Failure, Try}

trait Changeable {
  def execute[RETURN](command: OrganizationCommand[RETURN]): Try[RETURN] = {
    command match {
      case AddSimpleKanban(organizationId, name, prevalentSystem) => {
        val newKanban = Kanban.simpleOneWithName(name)
        prevalentSystem.addKanbanOn(organizationId, newKanban).asInstanceOf[Try[RETURN]]
      }
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}

object Changeable {

  trait OrganizationCommand[RETURN]

  case class AddSimpleKanban(
                              organizationId: Id,
                              name: String,
                              prevalentSystem: PrevalentSystem
                            ) extends OrganizationCommand[PrevalentSystem]

}
