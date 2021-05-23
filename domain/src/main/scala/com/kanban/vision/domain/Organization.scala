package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import scala.util.{Failure, Success, Try}
import java.util.UUID

case class Organization(
                         id: Id = UUID.randomUUID().toString,
                         audit: Audit = Audit(),
                         name: String,
                         boards: Map[Id, Board] = Map.empty,
                         projects: Map[Id, Project] = Map.empty
                       ) extends Domain {
  def boardById(boardId: Id) = boards.get(boardId) match {
    case Some(board) => Some(board.flow)
    case None => None
  }
  
  def boardByName(boardName: String) = boards.values.find {_.name == boardName}

  def allBoards(): List[Board] = boards.values.toList

  def addBoard(board: Board): Try[Organization] = {
    boardByName(board.name) match {
      case Some(_) => Failure(IllegalStateException(s"Already exixts a borad with name: ${board.name}"))
      case None => Success(this.copy(boards = boards.updated(board.id, board)))
    }
  }
}
