package com.kanban.vision.usecase

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain._
import com.kanban.vision.domain.commands.OrganizationQueryable.GetAllBoardsFrom
import com.kanban.vision.domain.commands.SimulationQueryable.GetSimulationFrom
import com.kanban.vision.usecase.SimulationUseCase
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success, Try}

class SimulationUseCaseSpec
  extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"
  val firstSimulationId: Id = "simulation-1"
  val defaultBoardName = "Default"

  "A System" - {

    "when already have one organization with simple simulation" - {
      val system = KanbanSystem(initialState)

      "should be able to get the existent simulation from organization" in {
        SimulationUseCase.query(GetSimulationFrom(firstOrganizationId, firstSimulationId, system)) match {
          case Success(Some(simulation)) => assert(simulation.id === firstSimulationId)
          case Success(None) => fail()
          case Failure(_) => fail()
        }
      }

      "should not be able to get a kanban from existent simulation" in {
        SimulationUseCase.query(GetAllBoardsFrom(firstOrganizationId, firstSimulationId, system)) match {
          case Success(Some(listOfBoards)) => assert(listOfBoards.size === 1)
          case Failure(_) => fail()
          case _ => fail()
        }
      }
    }
  }

  private def initialState: Map[Id, Organization] =
    val simulation = Simulation.simple(firstSimulationId)
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName, simulations = Map(firstSimulationId -> simulation))
      )
    )
}
