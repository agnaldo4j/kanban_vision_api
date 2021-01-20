package com.kanban.vision.usecase.board

import com.kanban.vision.usecase.board.Changeable.BoardCommand

import scala.util.{Failure, Try}

trait Changeable {
  def execute[RETURN](command: BoardCommand[RETURN]): Try[RETURN] = {
    command match {
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}

object Changeable {

  trait BoardCommand[RETURN]

}
