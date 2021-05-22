package com.kanban.vision.usecase.board

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem}
import com.kanban.vision.domain.commands.BoardQueryable.{BoardQuery, GetFlowFrom}
import com.kanban.vision.usecase.QueryPerformer

import scala.util.{Failure, Success, Try}

trait Queryable extends QueryPerformer[BoardQuery] {
  override def query[RETURN](query: BoardQuery): Try[RETURN] = {
    query match {
      case GetFlowFrom(organizationId, boardId, kanbanSystem) =>
        kanbanSystem.getFlowFrom(organizationId, boardId).asInstanceOf[Try[RETURN]]
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}

