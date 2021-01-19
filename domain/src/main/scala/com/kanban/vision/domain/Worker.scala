package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

import java.util.UUID

sealed abstract class Ability( val name: String)

case object Analyst extends Ability("Analyst")
case object Developer extends Ability("Developer")
case object QualityAssurance extends Ability("Quality Assurance")
case object Deployer extends Ability("Deployer")

case class Worker(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   abilities: List[Ability],
                   name: String
                 )

object Worker {
  def baseWorkers(): List[Worker] =  {
    List(
      Worker(name = "José", abilities = List(Analyst, Developer)),
      Worker(name = "Paulo", abilities = List(QualityAssurance, Deployer)),
      Worker(name = "Maria", abilities = List(Developer, QualityAssurance, Deployer)),
    )
  }
}
