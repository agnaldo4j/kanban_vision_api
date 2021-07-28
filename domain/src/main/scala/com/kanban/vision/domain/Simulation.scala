package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID
import scala.util.{Failure, Success, Try}

sealed abstract class SimulationType

case object SimpleSimulation extends SimulationType

case object ComplexSimulation extends SimulationType

object Simulation {
  def simple(id: Id = UUID.randomUUID().toString): Simulation = {
    val board = Board.simpleOneWithName("Default")
    val defaultProjects = Project.defaultProjectsToSimpleSimulation()
    Simulation(id = id, order = 0, boards = Map(board.id -> board), projects = defaultProjects)
  }
}

case class Simulation(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int,
                 boards: Map[Id, Board] = Map.empty,
                 projects: Map[Id, Project] = Map.empty,
                 completed: Boolean = false,
                 simulationType: SimulationType = SimpleSimulation,
                 durationInWeeks: Int = 40,
                 week: Int = 0,
                 day: Int = 0
               ) extends Domain with ComparableByInteger {

  def getFlowFrom(boardId: Id): Option[Flow] = boardById(boardId).map(_.flow)
  
  def boardById(boardId: Id) = boards.get(boardId)

  def boardByName(boardName: String) = boards.values.find {_.name == boardName}

  def allBoards(): List[Board] = boards.values.toList

  def addBoard(board: Board): Try[Simulation] = {
    boardByName(board.name) match {
      case Some(_) => Failure(new IllegalStateException(s"Already exixts a borad with name: ${board.name}"))
      case None => Success(this.copy(boards = boards ++ Map(board.id -> board)))
    }
  }
}
