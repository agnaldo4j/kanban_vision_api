package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem}

object BoardChangeable {

  trait BoardCommand[RETURN]

}

object BoardQueryable {

  trait BoardQuery[RETURN]

  case class GetFlowFrom(
                          organizationId: Id,
                          boardId: Id,
                          kanbanSystem: KanbanSystem
                        ) extends BoardQuery[Option[Flow]]

}

