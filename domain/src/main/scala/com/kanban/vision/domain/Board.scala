package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID

object Board {
  def simpleOneWithName(name: String): Board = {
    simpleOne(
      name = name,
      flow = Flow.simpleOne(),
      workers = Worker.baseWorkers()
    )
  }

  def simpleOne(
                 id: String = UUID.randomUUID().toString,
                 flow: Flow = Flow.simpleOne(),
                 workers: List[Worker] = Worker.baseWorkers(),
                 name: String
               ): Board = {
    new Board(
      id = id,
      name = name,
      flow = flow,
      workers = workers
    )
  }
}

case class Board(
                  id: Id = UUID.randomUUID().toString,
                  audit: Audit = Audit(),
                  flow: Flow = Flow(),
                  workers: List[Worker] = List.empty,
                  name: String
                ) extends Domain
