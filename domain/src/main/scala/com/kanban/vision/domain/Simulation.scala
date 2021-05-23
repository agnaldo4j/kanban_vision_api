package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

sealed abstract class SimulationType

case object SimpleSimulation extends SimulationType

case object ComplexSimulation extends SimulationType

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
               ) extends Domain with ComparableByInteger
