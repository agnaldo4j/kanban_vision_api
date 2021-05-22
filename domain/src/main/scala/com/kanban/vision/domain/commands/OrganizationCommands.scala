package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, KanbanSystem}

object OrganizationChangeable {

  trait OrganizationCommand

  case class AddSimpleBoard(
                             organizationId: Id,
                             name: String,
                             kanbanSystem: KanbanSystem
                           ) extends OrganizationCommand

}

object OrganizationQueryable {

  trait OrganizationQuery

  case class GetAllBoardsFrom(
                               organizationId: Id,
                               kanbanSystem: KanbanSystem
                             ) extends OrganizationQuery

}
