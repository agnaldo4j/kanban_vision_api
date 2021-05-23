package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, KanbanSystem}
import com.kanban.vision.domain.commands.OrganizationQueryable.{GetAllBoardsFrom, OrganizationQuery}

import scala.util.{Failure, Success, Try}

trait Queryable {
  def query[RETURN](query: OrganizationQuery[RETURN]): Try[RETURN] = query.execute()
}
