package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Board, Organization, KanbanSystem}
import com.kanban.vision.domain.commands.OrganizationChangeable.{AddSimpleBoard, OrganizationCommand}
import com.kanban.vision.domain.commands.OrganizationQueryable.{GetAllBoardsFrom, OrganizationQuery}
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success}

class OrganizationUseCaseSpec extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"

  "A System" - {
    "when empty" - {

      val system = KanbanSystem()

      "should not have any organization" in {
        val result = execute[List[Board]](GetAllBoardsFrom(firstOrganizationId, system))
        result match {
          case Success(kanbans: List[Board]) => kanbans === List.empty
          case _ => fail()
        }
      }

      "should not be able to add a Kanban to organization" in {
        execute(AddSimpleBoard(firstOrganizationId, "Default", system)) match {
          case Failure(error) =>
            error.getMessage === s"Not found organization with id: $firstOrganizationId"
          case _ => fail()
        }
      }
    }

    "when already have one organization" - {
      val system = KanbanSystem(initialState)

      "should be able to add an kanban on organization" in {
        execute(AddSimpleBoard(firstOrganizationId, "Default", system)) match {
          case Success(system) => system.allBoards(firstOrganizationId).size === 1
          case _ => fail()
        }
      }
    }
  }

  private def execute[RETURN](query: OrganizationQuery[RETURN]) = OrganizationUseCase.execute(query)

  private def execute[RETURN](command: OrganizationCommand[RETURN]) = OrganizationUseCase.execute(command)

  private def initialState: Map[Id, Organization] =
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName)
      )
    )
}
