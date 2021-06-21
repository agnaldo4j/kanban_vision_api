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
                                  kanbanSystem: KanbanSystem
                                ) extends OrganizationCommand[Simulation] {
    //TODO complete implementation: Add name and description on Simulation, change order by dateTime to order simulations
    def execute(): Try[KanbanSystemChanged[Simulation]] = {
      val simulation = Simulation.simple()
      val newState = kanbanSystem//kanbanSystem.addSimulation(organizationId, simulation)
      Success(
        KanbanSystemChanged(newState, simulation)
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
