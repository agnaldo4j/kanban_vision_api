package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

import java.util.UUID

sealed abstract class Ability( val name: String)

case object Analyst extends Ability("Analyst")
case object Developer extends Ability("Developer")
case object QualityAssurance extends Ability("Quality Assurance")

case class Worker(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   abilities: List[Ability],
                   name: String
                 )
