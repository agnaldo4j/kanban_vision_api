package com.kanban.vision.eventbus.system

import com.kanban.vision.eventbus.EventBusQuery.{GetOrganizationByNameFromSystem, Query}
import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.SystemQueryable.GetOrganizationByName
import com.kanban.vision.usecase.system.SystemUseCase

import scala.util.{Failure, Try}

trait SystemQueryable {
  var systemState: KanbanSystem

  def execute[RETURN](event: Query): Try[RETURN] = {
    event match {
      case GetOrganizationByNameFromSystem(name) => SystemUseCase.execute(GetOrganizationByName(name, systemState))
      case _ => Failure(new IllegalStateException(s"Event not found: $event"))
    }
  }
}

