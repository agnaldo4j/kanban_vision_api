package com.kanban.vision.usecase

import com.kanban.vision.domain.KanbanSystemChanged
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.BoardQueryable.BoardQuery
import com.kanban.vision.domain.commands.SystemChangeable.SystemCommand
import com.kanban.vision.domain.commands.SystemQueryable.SystemQueryable
import com.kanban.vision.usecase.board.BoardUseCase
import com.kanban.vision.usecase.system.SystemUseCase

import scala.util.Try

trait UseCaseExecutor[Queryable, Changeable, Usecase <: QueryPerformer[Queryable] & ChangePerformer[Changeable]] {
  val usecase: Usecase

  def query[RETURN](query: Queryable): Try[RETURN] = usecase.query(query)

  def change[RETURN](command: Changeable): Try[KanbanSystemChanged[RETURN]] = usecase.change(command)
}
