package com.kanban.vision.usecase.actions

import com.kanban.vision.domain.commands.BoardQueryable.BoardQuery
import com.kanban.vision.domain.commands.OrganizationQueryable.OrganizationQuery
import com.kanban.vision.domain.commands.SimulationQueryable.SimulationQuery
import com.kanban.vision.domain.commands.SystemQueryable.SystemQuery

import scala.util.Try

trait Queryable {
  def query[RETURN](
                     query: BoardQuery[RETURN]
                       | SimulationQuery[RETURN]
                       | OrganizationQuery[RETURN]
                       | SystemQuery[RETURN]
                   ): Try[RETURN] = query match {
    case boardQuery: BoardQuery[RETURN] => boardQuery.execute()
    case simulationQuery: SimulationQuery[RETURN] => simulationQuery.execute()
    case organizationQuery: OrganizationQuery[RETURN] => organizationQuery.execute()
    case systemQuery: SystemQuery[RETURN] => systemQuery.execute()
  }
}
