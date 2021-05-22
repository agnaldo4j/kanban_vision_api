package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID
import scala.util.{Try, Failure, Success}

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
  def getFlowFrom(organizationId: Id, boardId: Id): Try[Option[Flow]] = Success(
    organizations
      .get(organizationId)
      .flatMap(_.boardById(boardId))
  )

  def allBoards(organizationId: Id): Try[List[Board]] = {
    val result = organizations.get(organizationId) match {
      case Some(organization) => organization.allBoards()
      case None => List.empty
    }
    Success(result)
  }

  def addOrganization(organization: Organization) = organizationByName(organization.name) match {
    case Success(Some(existentOrganization)) => {
      Failure(IllegalStateException(s"Organization already exists with name: ${existentOrganization.name}"))
    }
    case Success(None) => {
      val newOrganizations = organizations ++ Map(organization.id -> organization)
      val newKanbanSystem = copy(organizations = newOrganizations)
      Success(KanbanSystemChanged[Organization](newKanbanSystem, organization))
    }
    case Failure(ex) => Failure(ex)
  }

  def organizationByName(name: String): Try[Option[Organization]] = Success(
    organizations.values.find {
      _.name == name
    }
  )

  def organizationById(organizationId: Id): Try[Option[Organization]] = Success(organizations.get(organizationId))

  def allOrganizations(): Try[List[Organization]] = Success(organizations.values.toList)

  def removeOrganization(organizationId: Id) = organizations.get(organizationId) match {
    case Some(organization) =>
      val newKanbanSystem = copy(organizations = organizations - organizationId)
      Success(KanbanSystemChanged[Option[Organization]](newKanbanSystem, Some(organization)))
    case None => Failure(IllegalStateException(s"Organization not found with id: $organizationId"))
  }

  def addBoardOn(organizationId: Id, board: Board) = organizations.get(organizationId) match {
    case Some(organization) => {
      organization.addBoard(board) match {
        case Success(newOrganizationState) => {
          val newOrganizations = organizations.updated(organization.id, newOrganizationState)
          val newSystemState = copy(organizations = newOrganizations)
          Success(
            KanbanSystemChanged(
              newSystemState,
              board
            )
          )
        }
        case Failure(ex) => Failure(ex)
      }
    }
    case None => Failure(
      new IllegalStateException(s"Not found organization with id: ${organizationId}")
    )
  }
}
