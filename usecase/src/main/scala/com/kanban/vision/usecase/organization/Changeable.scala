package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged}
import com.kanban.vision.domain.commands.OrganizationChangeable.{AddSimpleBoard, OrganizationCommand}

import scala.util.{Failure, Try}

trait Changeable {
  def change[RETURN](command: OrganizationCommand[RETURN]): Try[KanbanSystemChanged[RETURN]] = command.execute()
}


