package com.kanban.vision.usecase.board

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{KanbanSystemChanged, Flow, Board, KanbanSystem, Organization}
import com.kanban.vision.domain.commands.BoardChangeable.BoardCommand
import com.kanban.vision.domain.commands.BoardQueryable.{BoardQuery, GetFlowFrom}
import org.scalatest.freespec.AnyFreeSpec
import com.kanban.vision.usecase.UseCaseExecutor
import com.kanban.vision.usecase.board.BoardUseCase

import scala.util.{Try, Success}

class BoardUseCaseSpec 
  extends AnyFreeSpec 
    with UseCaseExecutor[BoardQuery, BoardCommand, BoardUseCase.type] {
  override val usecase = BoardUseCase
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"
  val simpleBoardId: Id = "board-1"

  "A System" - {
    "when already have one organization with a default board" - {
      val system = KanbanSystem(initialState)

      "should have default flow structure" in {
        query[Option[Flow]](GetFlowFrom(firstOrganizationId, simpleBoardId, system)) match {
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
