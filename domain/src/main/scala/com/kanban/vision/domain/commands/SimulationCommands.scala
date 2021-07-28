package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.commands.OrganizationChangeable.OrganizationCommand
import com.kanban.vision.domain.commands.OrganizationQueryable.OrganizationQuery
import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged, Simulation}

import scala.util.Try

object SimulationChangeable {

  trait SimulationCommand[RETURN] { 
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }

  case class AddSimpleBoard(
                             organizationId: Id,
                             simulationId: Id,
                             name: String,
                             kanbanSystem: KanbanSystem
                           ) extends SimulationCommand[Board] {
    override def execute(): Try[KanbanSystemChanged[Board]] = {
      kanbanSystem.addBoardOn(organizationId, simulationId, Board(name = name))
    }
  }
}

object SimulationQueryable {

  trait SimulationQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetAllBoardsFrom(
                               organizationId: Id,
                               simulationId: Id,
                               kanbanSystem: KanbanSystem
                             ) extends SimulationQuery[Option[List[Board]]] {
    override def execute(): Try[Option[List[Board]]] = kanbanSystem.allBoards(organizationId, simulationId)
  }
}

