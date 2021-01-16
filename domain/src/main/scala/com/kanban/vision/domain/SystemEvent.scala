package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.Id

object SystemEvent {

  trait SystemEvent

  case class AddOrganization(name: String) extends SystemEvent

  case class DeleteOrganization(id: Id) extends SystemEvent
}

