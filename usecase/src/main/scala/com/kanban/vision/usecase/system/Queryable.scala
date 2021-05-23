package com.kanban.vision.usecase.system

import com.kanban.vision.domain.commands.SystemQueryable.SystemQueryable

import scala.util.{Failure, Success, Try}

trait Queryable {
  def query[RETURN](query: SystemQueryable[RETURN]): Try[RETURN] = query.execute()
}
