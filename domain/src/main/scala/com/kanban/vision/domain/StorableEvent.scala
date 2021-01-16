package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

object StorableEvent {

  trait StorableEvent

  case class AddOrganization(name: String) extends StorableEvent

  case class DeleteOrganization(id: Id) extends StorableEvent
}

