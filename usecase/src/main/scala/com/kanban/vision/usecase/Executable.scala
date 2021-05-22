package com.kanban.vision.usecase

import com.kanban.vision.domain.KanbanSystemChanged
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.BoardQueryable.BoardQuery

import scala.util.{Failure, Try}

trait ChangePerformer[COMMAND] {
  def change[RETURN](command: COMMAND): Try[KanbanSystemChanged[RETURN]]
}

trait QueryPerformer[QUERY] {
  def query[RETURN](query: QUERY): Try[RETURN]
}
