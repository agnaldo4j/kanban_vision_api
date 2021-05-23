package com.kanban.vision.usecase.board

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Flow, KanbanSystem}
import com.kanban.vision.domain.commands.BoardQueryable.{BoardQuery, GetFlowFrom}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def query[RETURN](query: BoardQuery[RETURN]): Try[RETURN] = query.execute()
}

