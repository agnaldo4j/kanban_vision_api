package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import scala.util.{Failure, Success, Try}
import java.util.UUID

case class Organization(
                         id: Id = UUID.randomUUID().toString,
                         audit: Audit = Audit(),
                         name: String,
                         simulations: Map[Id, Simulation] = Map.empty,
                       ) extends Domain {

  def addSimulation(simulation: Simulation): Try[Organization] = Success(
    this.copy(simulations = simulations.updated(simulation.id, simulation))
  )

  def allSimulations(): Option[List[Simulation]] = Some(simulations.values.toList)

  def getFlowFrom(simulationId: Id, boardId: Id): Option[Flow] = simulationById(simulationId).flatMap(_.getFlowFrom(boardId))

  def simulationById(simulationId: Id): Option[Simulation] = simulations.get(simulationId)

  def boardById(simulationId: Id, boardId: Id) = simulationById(simulationId).map(_.boardById(boardId))

  def boardByName(simulationId: Id, boardName: String) = simulationById(simulationId).map(_.boardByName(boardName))

  def allBoards(simulationId: Id): Option[List[Board]] = simulationById(simulationId).map(_.allBoards())

  def addBoard(simulationId: Id, board: Board): Try[Organization] = {
    simulationById(simulationId) match {
      case Some(simulation) => simulation.addBoard(board).map { newSimulation =>
        this.copy(simulations = simulations.updated(simulationId, newSimulation))
      }
      case None => Failure(new IllegalStateException(s"Simulation not found with id: $simulationId"))
    }
  }
}
