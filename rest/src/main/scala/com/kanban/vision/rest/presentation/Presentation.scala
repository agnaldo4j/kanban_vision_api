package com.kanban.vision.rest.presentation

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.Organization
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object Presentation {

  case class NewOrganization(name: String)

  case class AddedOrganization(id: Id, name: String)

  object AddedOrganization {
    def apply(organization: Organization): AddedOrganization = AddedOrganization(organization.id, organization.name)
  }

  implicit val encoder: Encoder[AddedOrganization] = deriveEncoder[AddedOrganization]
  implicit val decoder: Decoder[NewOrganization] = deriveDecoder[NewOrganization]
}
