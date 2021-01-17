package com.kanban.vision.domain

import com.kanban.vision.domain.Domain.{Domain, Id}

import java.util.UUID
import scala.util.{Failure, Success, Try}

object PrevalentSystem {
  def apply(): PrevalentSystem = new PrevalentSystem()

  def apply(initialState: Map[String, Organization]): PrevalentSystem =
    new PrevalentSystem(organizations = initialState)
}

case class PrevalentSystem(
                   id: Id = UUID.randomUUID().toString,
                   audit: Audit = Audit(),
                   organizations: Map[Id, Organization] = Map.empty
                 ) extends Domain {
  def addOrganization(organization: Organization): PrevalentSystem = copy(
    organizations = organizations ++ Map(organization.id -> organization)
  )

  def organizationByName(name: String) = organizations.values.find { organization =>
    organization.name == name
  }

  def organizationById(organizationId: Id) = organizations.get(organizationId)

  def allOrganizations() = organizations.values.toList

  def removeOrganization(organizationId: Id): PrevalentSystem = {
    this.copy(organizations = organizations - organizationId)
  }

  def addKanbanOn(organizationId: Id, kanban: Kanban): Try[PrevalentSystem] = {
    organizations.get(organizationId) match {
      case Some(organization) => Success(
        this.copy(
          organizations = organizations.updated(organization.id, organization.addKanban(kanban))
        )
      )
      case None => Failure(
        new IllegalStateException(s"Not found organization with id: ${organizationId}")
      )
    }
  }
}
