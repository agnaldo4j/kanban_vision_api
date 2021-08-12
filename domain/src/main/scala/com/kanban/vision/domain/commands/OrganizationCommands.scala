package com.kanban.vision.domain.commands

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.commands.SimulationQueryable.SimulationQuery
import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged, Simulation}

import scala.util.{Success, Try}

object OrganizationChangeable {

  trait OrganizationCommand[RETURN] {
    def execute(): Try[KanbanSystemChanged[RETURN]]
  }

  case class AddSimpleSimulation(
                                  organizationId: Id,
                                  name: String,
                                  description: String,
                                  kanbanSystem: KanbanSystem
                                ) extends OrganizationCommand[Simulation] {
    def execute(): Try[KanbanSystemChanged[Simulation]] = {
      kanbanSystem
        .addSimulation(
          organizationId,
          Simulation.simple(name = name, description = description)
        )
    }
  }
}

object OrganizationQueryable {

  trait OrganizationQuery[RETURN] {
    def execute(): Try[RETURN]
  }

  case class GetSimulationFrom(
                                organizationId: Id,
                                simulationId: Id,
                                kanbanSystem: KanbanSystem
                              ) extends OrganizationQuery[Option[Simulation]] {
    override def execute(): Try[Option[Simulation]] = kanbanSystem.getSimulationFrom(organizationId, simulationId)
  }

  case class GetAllSimulationsFrom(
                                    organizationId: Id,
                                    kanbanSystem: KanbanSystem
                                  ) extends OrganizationQuery[Option[List[Simulation]]] {
    override def execute(): Try[Option[List[Simulation]]] = kanbanSystem.getAllSimulationsFrom(organizationId)
  }
}
