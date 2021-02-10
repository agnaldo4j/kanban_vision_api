package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

object StorableEvent {

  trait StorableEvent

  case class AddOrganizationOnSystem(name: String) extends StorableEvent

  case class DeleteOrganizationFromSystem(id: Id) extends StorableEvent
}
