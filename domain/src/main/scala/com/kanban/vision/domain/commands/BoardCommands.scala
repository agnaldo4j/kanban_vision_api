package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem}

object BoardChangeable {

  trait BoardCommand

}

object BoardQueryable {

  trait BoardQuery

  case class GetFlowFrom(
                          organizationId: Id,
                          boardId: Id,
                          kanbanSystem: KanbanSystem
                        ) extends BoardQuery

}

