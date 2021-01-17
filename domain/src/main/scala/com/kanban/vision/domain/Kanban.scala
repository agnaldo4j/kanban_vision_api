package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID

object Kanban {
  def simpleOneWithName(name: String) = new Kanban(name = name)
}

case class Kanban(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   flow: Flow = Flow(),
                   name: String
                 ) extends Domain
