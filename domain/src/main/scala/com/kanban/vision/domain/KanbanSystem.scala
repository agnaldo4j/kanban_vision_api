package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID
import scala.util.{Failure, Success}

object KanbanSystem {
  def apply(): KanbanSystem = new KanbanSystem()

  def apply(initialState: Map[String, Organization]): KanbanSystem =
    new KanbanSystem(organizations = initialState)
}

case class KanbanSystem(
                         id: Id = UUID.randomUUID().toString,
                         audit: Audit = Audit(),
                         organizations: Map[Id, Organization] = Map.empty
                       ) extends Domain {
  def getFlowFrom(organizationId: Id, boardId: Id): Option[Flow] = {
    organizations
      .get(organizationId)
      .flatMap(_.boardById(boardId))
  }

  def allBoards(organizationId: Id): List[Board] = organizations.get(organizationId) match {
    case Some(organization) => organization.allBoards()
    case None => List.empty
  }

  def addOrganization(organization: Organization): KanbanSystem = copy(
    organizations = organizations ++ Map(organization.id -> organization)
  )

  def organizationByName(name: String) = organizations.values.find { organization =>
    organization.name == name
  }

  def organizationById(organizationId: Id) = organizations.get(organizationId)

  def allOrganizations() = organizations.values.toList

  def removeOrganization(organizationId: Id): KanbanSystem = {
    this.copy(organizations = organizations - organizationId)
  }

  def addBoardOn(organizationId: Id, board: Board) = organizations.get(organizationId) match {
    case Some(organization) => Success(
      this.copy(
        organizations = organizations.updated(organization.id, organization.addBoard(board))
      )
    )
    case None => Failure(
      new IllegalStateException(s"Not found organization with id: ${organizationId}")
    )
  }
}
