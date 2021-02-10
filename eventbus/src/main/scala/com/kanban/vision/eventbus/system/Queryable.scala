package com.kanban.vision.eventbus.system

import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.SystemQueryable.SystemQuery
import com.kanban.vision.usecase.system.SystemUseCase

import scala.util.Try

trait SystemQueryable {
  var systemState: KanbanSystem

  def execute[RETURN](event: SystemQuery): Try[RETURN] = SystemUseCase.execute(event)
}

