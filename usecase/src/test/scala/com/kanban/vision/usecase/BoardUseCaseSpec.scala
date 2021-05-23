package com.kanban.vision.usecase

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.BoardQueryable.{BoardQuery, GetFlowFrom}
import com.kanban.vision.domain._
import com.kanban.vision.usecase.BoardUseCase
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Success, Try}

class BoardUseCaseSpec 
  extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"
  val simpleBoardId: Id = "board-1"

  "A System" - {
    "when already have one organization with a default board" - {
      val system = KanbanSystem(initialState)

      "should have default flow structure" in {
        BoardUseCase.query(GetFlowFrom(firstOrganizationId, simpleBoardId, system)) match {
          case Success(Some(flow)) => assert(flow.steps.size === 9)
          case _ => fail()
        }
      }
    }
  }

  private def initialState: Map[Id, Organization] = {
    val boards = Map(simpleBoardId -> Board.simpleOne(id = simpleBoardId, name = "Default"))
    val organization = Organization(id = firstOrganizationId, name = organizationName, boards = boards)
    Map(firstOrganizationId -> organization)
  }
}
