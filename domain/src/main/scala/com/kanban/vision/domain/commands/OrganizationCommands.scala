package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged}
import scala.util.Try

object OrganizationChangeable {

  trait OrganizationCommand[RETURN] {
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }

  case class AddSimpleBoard(
                             organizationId: Id,
                             name: String,
                             kanbanSystem: KanbanSystem
                           ) extends OrganizationCommand[Board] {
    override def execute(): Try[KanbanSystemChanged[Board]] = {
      kanbanSystem.addBoardOn(organizationId, Board(name = name))
    }
  }

}

object OrganizationQueryable {

  trait OrganizationQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetAllBoardsFrom(
                               organizationId: Id,
                               kanbanSystem: KanbanSystem
                             ) extends OrganizationQuery[List[Board]] {
    override def execute(): Try[List[Board]] = kanbanSystem.allBoards(organizationId)
  }

}
