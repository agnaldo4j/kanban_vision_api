package com.kanban.vision.usecase.board

import com.kanban.vision.domain.KanbanSystemChanged
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.usecase.ChangePerformer

import scala.util.{Failure, Try}

trait Changeable extends ChangePerformer[BoardCommand] {
  override def change[RETURN](command: BoardCommand): Try[KanbanSystemChanged[RETURN]] = {
    command match {
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}
