package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID

case class Organization(
                         id: Id = UUID.randomUUID().toString,
                         audit: Audit = Audit(),
                         name: String,
                         kanbans: Map[Id, Board] = Map.empty
                       ) extends Domain {
  def allKanbans(): List[Board] = kanbans.values.toList

  def addKanban(kanban: Board): Organization = {
    this.copy(kanbans = kanbans.updated(kanban.id, kanban))
  }
}
