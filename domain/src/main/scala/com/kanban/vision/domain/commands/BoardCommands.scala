package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem, KanbanSystemChanged}
import scala.util.Try

object BoardChangeable {

  trait BoardCommand[RETURN] {
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }
}

object BoardQueryable {

  trait BoardQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetFlowFrom(
                          organizationId: Id,
                          boardId: Id,
                          kanbanSystem: KanbanSystem
                        ) extends BoardQuery[Option[Flow]] {
    override def execute(): Try[Option[Flow]] = kanbanSystem.getFlowFrom(organizationId, boardId)
  }
}

