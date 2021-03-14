package com.kanban.vision.eventbus.system

import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.eventbus.EventBusCommand.{AddOrganizationOnSystem, Command}
import com.kanban.vision.domain.SystemChangeable.AddOrganization
import com.kanban.vision.eventbus.Storage
import com.kanban.vision.usecase.system.SystemUseCase

import scala.util.{Failure, Try}

trait SystemChangeable {
  val storage: Storage
  var systemState: KanbanSystem

  def execute[DOMAIN](event: Command): Try[(KanbanSystem, DOMAIN)] = {
    storage.log(event)
    event match {
      case AddOrganizationOnSystem(name) => executeAddOrganization(name)
      case _ => Failure(new IllegalStateException(s"Event not found: $event"))
    }
  }

  private def executeAddOrganization[DOMAIN](name: String): Try[(KanbanSystem, DOMAIN)] = {
    SystemUseCase.execute(AddOrganization(name, systemState))
  }
}
