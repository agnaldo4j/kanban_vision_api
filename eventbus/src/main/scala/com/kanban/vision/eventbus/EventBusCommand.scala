package com.kanban.vision.eventbus

import com.kanban.vision.domain.Domain.Id

object EventBusCommand {

  trait Command

  case class AddOrganizationOnSystem(name: String) extends Command

  case class DeleteOrganizationFromSystem(id: Id) extends Command
}

object EventBusQuery {

  trait Query

  case class GetOrganizationByNameFromSystem(name: String) extends Query

  case class GetOrganizationByIdFromSystem(id: Id) extends Query

  case class GetAllOrganizationsFromSystem() extends Query
}
