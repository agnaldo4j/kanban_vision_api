package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.{Id, Organization, System}
import com.kanban.vision.usecase.system.Changeable.{AddOrganization, DeleteOrganization, SystemCommand}
import com.kanban.vision.usecase.system.Queryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQuery}
import com.kanban.vision.usecase.system.SystemUseCase.{ManyResult, SingleResult, Success, SystemResult}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class SystemUseCaseSpec extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"

  "A System" - {
    "when empty" - {

      val system = System()

      "should not have any organization" in {
        execute(GetAllOrganizations(system)) match {
          case ManyResult(organizations) => organizations shouldBe List.empty
          case _                         => fail()
        }
      }

      "should be able to add an organization" in {
        execute(AddOrganization(organizationName, system)) match {
          case Success(newSystemState) =>
            newSystemState.organizations.nonEmpty shouldBe true
            newSystemState.organizations.values.head.name shouldBe organizationName
          case _ => fail()
        }
      }
    }

    "when already have one organization" - {
      val system = System(initialState)

      "should be able to add an organization" in {
        execute(AddOrganization("New Organization", system)) match {
          case Success(newSystemState) =>
            newSystemState.organizations.values.size shouldBe 2
          case _ => fail()
        }
      }

      "should be able to search an organization by name" in {
        execute(GetOrganizationByName(organizationName, system)) match {
          case SingleResult(result) =>
            result match {
              case Some(organization) =>
                organization.name shouldBe organizationName
                organization.id shouldBe firstOrganizationId
              case None => fail()
            }
          case _ => fail()
        }
      }

      "should be able to search an organization by id" in {
        execute(GetOrganizationById(firstOrganizationId, system)) match {
          case SingleResult(result) =>
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
        execute(DeleteOrganization(firstOrganizationId, system)) match {
          case Success(newSystemState) =>
            newSystemState.organizations shouldBe Map.empty
          case _ => fail()
        }
      }
    }
  }

  def execute(query: SystemQuery): SystemResult = SystemUseCase.execute(query)

  def execute(command: SystemCommand): SystemResult =
    SystemUseCase.execute(command)

  def initialState: Map[Id, Organization] =
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName)
      )
    )

  implicit def SystemResultToSuccess(result: SystemResult): Success =
    result.asInstanceOf[Success]
}
