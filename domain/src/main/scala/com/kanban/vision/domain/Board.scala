package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID

object Board {
  def simpleOneWithName(name: String): Board = {
    val flow = Flow.simpleOne()
    new Board(name = name, flow = flow)
  }
}

case class Board(
                  id: Id = UUID.randomUUID().toString,
                  audit: Audit = Audit(),
                  flow: Flow = Flow(),
                  workers: List[Worker] = List.empty,
                  name: String
                ) extends Domain
