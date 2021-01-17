package com.kanban.vision.usecase.organization

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.{Kanban, Organization, PrevalentSystem}
import com.kanban.vision.usecase.organization.Changeable.{AddSimpleKanban, OrganizationCommand}
import com.kanban.vision.usecase.organization.Queryable.{GetAllKanbansFrom, OrganizationQuery}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.util.{Failure, Success}

class OrganizationUseCaseSpec extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"

  "A System" - {
    "when empty" - {

      val system = PrevalentSystem()

      "should not have any organization" in {
        val result = execute[List[Kanban]](GetAllKanbansFrom("organization-id", system))
        result match {
          case Success(kanbans: List[Kanban]) => kanbans shouldBe List.empty
          case _                         => fail()
        }
      }

      "should not be able to add a Kanban to organization" in {
        execute(AddSimpleKanban("organization-id", "Default", system)) match {
          case Failure(error) =>
            error.getMessage shouldBe "Not found organization with id: organization-id"
          case _ => fail()
        }
      }
    }
  }

  private def execute[RETURN](query: OrganizationQuery[RETURN]) = OrganizationUseCase.execute(query)

  private def execute[RETURN](command: OrganizationCommand[RETURN]) = OrganizationUseCase.execute(command)

  private def initialState: Map[Id, Organization] =
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName)
      )
    )
}
