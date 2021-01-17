package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

import java.util.UUID

case class Worker(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   order: Int
                 )
