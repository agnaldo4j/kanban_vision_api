package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Organization, PrevalentSystem}
import com.kanban.vision.usecase.system.Changeable.{AddOrganization, DeleteOrganization, SystemCommand}

import scala.util.{Failure, Success, Try}

trait Changeable {
  def execute[RETURN](command: SystemCommand[RETURN]): Try[RETURN] = {
    command match {
      case AddOrganization(name, prevalentSystem) =>
        Success(prevalentSystem.addOrganization(Organization(name = name)).asInstanceOf[RETURN])
      case DeleteOrganization(id, system) =>
        Success(system.removeOrganization(id).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}

object Changeable {

  trait SystemCommand[RETURN]

  case class AddOrganization(name: String, prevalentSystem: PrevalentSystem) extends SystemCommand[PrevalentSystem]

  case class DeleteOrganization(id: Id, prevalentSystem: PrevalentSystem) extends SystemCommand[PrevalentSystem]

}
