package com.kanban.vision.usecase.actions

import com.kanban.vision.domain.KanbanSystemChanged
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.OrganizationChangeable.OrganizationCommand
import com.kanban.vision.domain.commands.SystemChangeable.SystemCommand

import scala.util.Try

trait Changeable {
  def change[RETURN](
                      command: BoardCommand[RETURN]
                        | OrganizationCommand[RETURN]
                        | SystemCommand[RETURN]
                    ): Try[KanbanSystemChanged[RETURN]] = command match {
    case boardCommand: BoardCommand[RETURN] => boardCommand.execute()
    case organizationCommand: OrganizationCommand[RETURN] => organizationCommand.execute()
    case systemCommand: SystemCommand[RETURN] => systemCommand.execute()
  }
}
