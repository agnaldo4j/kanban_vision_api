package com.kanban.vision.eventbus.system

import com.kanban.vision.adapters.Storage
import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.StorableEvent.{AddOrganizationOnSystem, StorableEvent}
import com.kanban.vision.domain.SystemChangeable.AddOrganization
import com.kanban.vision.usecase.system.SystemUseCase

import scala.util.{Failure, Try}

trait SystemChangeable {
  val storage: Storage
  var systemState: KanbanSystem

  def execute(event: StorableEvent): Try[KanbanSystem] = {
    storage.log(event)
    event match {
      case AddOrganizationOnSystem(name) => executeAddOrganization(name)
      case _ => Failure(new IllegalStateException(s"Event not found: $event"))
    }
  }

  private def executeAddOrganization(name: String): Try[KanbanSystem] = {
    val result = SystemUseCase.execute(AddOrganization(name, systemState))
    if (result.isSuccess) systemState = result.get
    result
  }
}
