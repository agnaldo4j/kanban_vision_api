package com.kanban.vision.usecase.board

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{KanbanSystemChanged, Board, KanbanSystem, Organization}
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.BoardQueryable.{BoardQuery, GetFlowFrom}
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Try, Success}

class BoardUseCaseSpec extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"
  val simpleBoardId: Id = "board-1"

  "A System" - {
    "when already have one organization with a default board" - {
      val system = KanbanSystem(initialState)

      "should have default flow structure" in {
        execute(GetFlowFrom(firstOrganizationId, simpleBoardId, system)) match {
          case Success(Some(flow)) => assert(flow.steps.size === 9)
          case _ => fail()
        }
      }
    }
  }

  private def execute[RETURN](query: BoardQuery[RETURN]) = BoardUseCase.execute(query)

  private def execute[RETURN](command: BoardCommand): Try[KanbanSystemChanged[RETURN]] = BoardUseCase.execute(command)

  private def initialState: Map[Id, Organization] = {
    val boards = Map(simpleBoardId -> Board.simpleOne(id = simpleBoardId, name = "Default"))
    val organization = Organization(id = firstOrganizationId, name = organizationName, boards = boards)
    Map(firstOrganizationId -> organization)
  }
}
