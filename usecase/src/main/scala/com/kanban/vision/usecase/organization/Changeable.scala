package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{KanbanSystemChanged, Board, KanbanSystem}
import com.kanban.vision.domain.commands.OrganizationChangeable.{AddSimpleBoard, OrganizationCommand}

import scala.util.{Failure, Try}

trait Changeable {
  def execute[RETURN](command: OrganizationCommand): Try[KanbanSystemChanged[RETURN]] = {
    command match {
      case AddSimpleBoard(organizationId, name, kanbanSystem) => {
        val newKanban = Board.simpleOneWithName(name)
        kanbanSystem
          .addBoardOn(organizationId, newKanban)
          .asInstanceOf[Try[KanbanSystemChanged[RETURN]]]
      }
      case _ => Failure(new IllegalStateException(s"Command not found ${command}"))
    }
  }
}


