package com.kanban.vision.usecase.system

import com.kanban.vision.domain.commands.SystemChangeable.{AddOrganization, DeleteOrganization, SystemChangeable}
import com.kanban.vision.domain.{KanbanSystemChanged, Organization}

import scala.util.{Failure, Success, Try}

trait Changeable {
  def change[RETURN](command: SystemChangeable[RETURN]): Try[KanbanSystemChanged[RETURN]] = command.execute()
}
