package com.kanban.vision.usecase.board

import com.kanban.vision.domain.KanbanSystemChanged
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand

import scala.util.{Failure, Try}

trait Changeable {
  def change[RETURN](command: BoardCommand[RETURN]): Try[KanbanSystemChanged[RETURN]] = command.execute()
}
