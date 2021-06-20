package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Simulation, KanbanSystem, KanbanSystemChanged}
import scala.util.Try

object SimulationChangeable {

  trait SimulationCommand[RETURN] {
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }
}

object SimulationQueryable {

  trait SimulationQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetSimulationFrom(
                          organizationId: Id,
                          simulationId: Id,
                          kanbanSystem: KanbanSystem
                        ) extends SimulationQuery[Option[Simulation]] {
    override def execute(): Try[Option[Simulation]] = kanbanSystem.getSimulationFrom(organizationId, simulationId)
  }
}

