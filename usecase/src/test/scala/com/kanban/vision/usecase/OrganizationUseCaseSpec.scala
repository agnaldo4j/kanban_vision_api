package com.kanban.vision.usecase

//import com.kanban.vision.domain.Domain.Id
//import com.kanban.vision.domain.commands.OrganizationChangeable.{OrganizationCommand, AddSimpleSimulation}
//import com.kanban.vision.domain.commands.OrganizationQueryable.{OrganizationQuery, GetAllSimulationsFrom}
//import com.kanban.vision.domain.{Board, KanbanSystem, KanbanSystemChanged, Organization, Simulation}
//import com.kanban.vision.usecase.OrganizationUseCase
//import org.scalatest.freespec.AnyFreeSpec
//
//import scala.util.{Failure, Success, Try}
//
//class OrganizationUseCaseSpec
//  extends AnyFreeSpec {
//  val organizationName = "Company"
//  val firstOrganizationId: Id = "organization-1"
//  val firstSimulationId: Id = "simulation-1"
//  val defaultBoardName = "Default"
//
//  "A System" - {
//    "when empty" - {
//
//      val system = KanbanSystem()
//
//      "should have one simulation" in {
//        OrganizationUseCase.query[Option[List[Simulation]]](GetAllSimulationsFrom(firstSimulationId, system)) match {
//          case Success(None) => assert(true)
//          case _ => fail()
//        }
//      }
//
//      //"should not be able to add a simulation to organization" in {
//      //  OrganizationUseCase.change(AddSimpleSimulation(firstOrganizationId, system)) match {
//      //    case Failure(error) =>
//      //      assert(error.getMessage === s"Not found organization with id: $firstOrganizationId")
//      //    case _ => fail()
//      //  }
//      //}
//    }
//
//    "when already have one organization" - {
//      val system = KanbanSystem(initialState)
//
//      "should be able to add an simulation on organization" in {
//        OrganizationUseCase.change(AddSimpleSimulation(firstOrganizationId, system)) match {
//          case Success(KanbanSystemChanged(system, board)) => {
//            val Success(boards) = system.allBoards(firstOrganizationId, firstSimulationId)
//            assert(boards.size === 1)
//          }
//          case Failure(_) => fail()
//        }
//      }
//    }
//  }
//
//  private def initialState: Map[Id, Organization] =
//    val simulation = Simulation.simple(id = firstSimulationId)
//    Map(
//      (
//        firstOrganizationId,
//        Organization(id = firstOrganizationId, name = organizationName, simulations = Map(firstSimulationId -> simulation))
//      )
//    )
//}
