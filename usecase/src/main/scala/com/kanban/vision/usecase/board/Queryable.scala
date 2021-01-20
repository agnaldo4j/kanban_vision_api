package com.kanban.vision.usecase.board

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem}
import com.kanban.vision.usecase.board.Queryable.{BoardQuery, GetFlowFrom}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def execute[RETURN](query: BoardQuery[RETURN]): Try[RETURN] = {
    query match {
      case GetFlowFrom(organizationId, boardId, kanbanSystem) =>
        Success(kanbanSystem.getFlowFrom(organizationId, boardId).asInstanceOf[RETURN])
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

object Queryable {

  trait BoardQuery[RETURN]

  case class GetFlowFrom(
                          organizationId: Id,
                          boardId: Id,
                          kanbanSystem: KanbanSystem
                        ) extends BoardQuery[Option[Flow]]

}

