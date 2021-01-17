package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{ComparableByInteger, Domain, Id}

import java.util.UUID

case class Task(
                 id: Id = UUID.randomUUID().toString,
                 audit: Audit = Audit(),
                 order: Int
               ) extends Domain with ComparableByInteger
