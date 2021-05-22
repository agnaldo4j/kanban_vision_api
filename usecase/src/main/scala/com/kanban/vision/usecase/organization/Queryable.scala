package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, KanbanSystem}
import com.kanban.vision.domain.commands.OrganizationQueryable.{GetAllBoardsFrom, OrganizationQuery}
import com.kanban.vision.usecase.QueryPerformer

import scala.util.{Failure, Success, Try}

trait Queryable extends QueryPerformer[OrganizationQuery] {
  override def query[RETURN](query: OrganizationQuery): Try[RETURN] = {
    query match {
      case GetAllBoardsFrom(organizationId, kanbanSystem) =>
        kanbanSystem.allBoards(organizationId).asInstanceOf[Try[RETURN]]
      case _ => Failure(new IllegalStateException(s"Command not found: $query"))
    }
  }
}
