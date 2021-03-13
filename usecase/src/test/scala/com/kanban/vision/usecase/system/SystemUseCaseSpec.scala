package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.SystemChangeable.{AddOrganization, DeleteOrganization, SystemCommand}
import com.kanban.vision.domain.SystemQueryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQueryable}
import com.kanban.vision.domain.{KanbanSystem, Organization}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.util.{Success, Try}

class SystemUseCaseSpec extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"

  "A System" - {
    "when empty" - {

      val system = KanbanSystem()

      "should not have any organization" in {
        val result = execute[List[Organization]](GetAllOrganizations(system))
        result match {
          case Success(organizations: List[Organization]) => organizations shouldBe List.empty
          case _                         => fail()
        }
      }

      "should be able to add an organization" in {
        execute[Organization](AddOrganization(organizationName, system)) match {
          case Success((newSystemState, organization)) =>
            newSystemState.organizations.nonEmpty shouldBe true
            newSystemState.organizations.values.head.name shouldBe organizationName
            organization.name shouldBe organizationName
          case _ => fail()
        }
      }
    }

    "when already have one organization" - {
      val system = KanbanSystem(initialState)

      "should be able to add an organization" in {
        execute[Organization](AddOrganization("New Organization", system)) match {
          case Success((newSystemState, organization)) =>
            newSystemState.organizations.values.size shouldBe 2
            organization.name shouldBe "New Organization"
          case _ => fail()
        }
      }

      "should be able to search an organization by name" in {
        execute[Option[Organization]](GetOrganizationByName(organizationName, system)) match {
          case Success(result) =>
            result match {
              case Some(organization: Organization) =>
                organization.name shouldBe organizationName
                organization.id shouldBe firstOrganizationId
              case None => fail()
            }
          case _ => fail()
        }
      }

      "should be able to search an organization by id" in {
        execute[Option[Organization]](GetOrganizationById(firstOrganizationId, system)) match {
          case Success(result) =>
            result match {
              case Some(organization) =>
                organization.name shouldBe organizationName
                organization.id shouldBe firstOrganizationId
              case None => fail()
            }
          case _ => fail()
        }
      }

      "should be able to delete an organization by id" in {
        execute[Option[Organization]](DeleteOrganization(firstOrganizationId, system)) match {
          case Success((newSystemState, _)) =>
            newSystemState.organizations shouldBe Map.empty
          case _ => fail()
        }
      }
    }
  }

  private def execute[RETURN](query: SystemQueryable): Try[RETURN] = SystemUseCase.execute(query)

  private def execute[RETURN](command: SystemCommand): Try[(KanbanSystem, RETURN)] = SystemUseCase.execute(command)

  private def initialState: Map[Id, Organization] =
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName)
      )
    )
}
