package com.kanban.vision.usecase

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.commands.OrganizationChangeable.{AddSimpleBoard, OrganizationCommand}
import com.kanban.vision.domain.commands.OrganizationQueryable.{GetAllBoardsFrom, OrganizationQuery}
import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged, Organization, Simulation}
import com.kanban.vision.usecase.OrganizationUseCase
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success, Try}

class OrganizationUseCaseSpec
  extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"
  val firstSimulationId: Id = "simulation-1"
  val defaultBoardName = "Default"

  "A System" - {
    "when empty" - {

      val system = KanbanSystem()

      "should not have any organization" in {
        OrganizationUseCase.query(GetAllBoardsFrom(firstOrganizationId, firstSimulationId, system)) match {
          case Success(kanbans: Option[List[Board]]) => assert(kanbans === None)
          case _ => fail()
        }
      }

      "should not be able to add a Kanban to organization" in {
        OrganizationUseCase.change(AddSimpleBoard(firstOrganizationId, firstSimulationId, defaultBoardName, system)) match {
          case Failure(error) =>
            assert(error.getMessage === s"Not found organization with id: $firstOrganizationId")
          case _ => fail()
        }
      }
    }

    "when already have one organization" - {
      val system = KanbanSystem(initialState)

      "should be able to add an kanban on organization" in {
        OrganizationUseCase.change(AddSimpleBoard(firstOrganizationId, firstSimulationId, defaultBoardName, system)) match {
          case Success(KanbanSystemChanged(system, board)) => {
            val Success(boards) = system.allBoards(firstOrganizationId, firstSimulationId)
            assert(boards.size === 1)
          }
          case Failure(_) => fail()
        }
      }
    }

    "when already have one organization with one board" - {
      val Success(KanbanSystemChanged(system, _)) = KanbanSystem(initialState)
        .addBoardOn(
          firstOrganizationId,
          firstSimulationId,
          Board(name = defaultBoardName)
        )

      "shouldn't be able to add an kanban with default name on organization" in {
        OrganizationUseCase.change(AddSimpleBoard(firstOrganizationId, firstSimulationId, defaultBoardName, system)) match {
          case Success(_) => fail()
          case Failure(ex) => assert(ex.getMessage === "Already exixts a borad with name: Default")
        }
      }

      "should be able to add an kanban with new name on organization" in {
        OrganizationUseCase.change(AddSimpleBoard(firstOrganizationId, firstSimulationId, "New", system)) match {
          case Success(KanbanSystemChanged(newSystem, board)) => {
            val Success(Some(boards)) = newSystem.allBoards(firstOrganizationId, firstSimulationId)
            assert(boards.contains(board) === true)
            assert(boards.size === 2)
          }
          case Failure(_) => fail()
        }
      }
    }
  }

  private def initialState: Map[Id, Organization] =
    val simulation = Simulation(id = firstOrganizationId, order = 0)
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName, simulations = Map(firstSimulationId -> simulation))
      )
    )
}
