package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID

case class Organization(
                         id: Id = UUID.randomUUID().toString,
                         audit: Audit = Audit(),
                         name: String,
                         boards: Map[Id, Board] = Map.empty
                       ) extends Domain {
  def boardById(boardId: Id) = boards.get(boardId) match {
    case Some(board) => Some(board.flow)
    case None => None
  }

  def allBoards(): List[Board] = boards.values.toList

  def addBoard(board: Board): Organization = {
    this.copy(boards = boards.updated(board.id, board))
  }
}
