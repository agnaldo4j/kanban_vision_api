package com.kanban.vision.usecase.system

import com.kanban.vision.domain.Domain.Id
import com.kanban.vision.domain.commands.SystemChangeable.{AddOrganization, DeleteOrganization, SystemChangeable}
import com.kanban.vision.domain.commands.SystemQueryable.{GetAllOrganizations, GetOrganizationById, GetOrganizationByName, SystemQueryable}
import com.kanban.vision.domain.{KanbanSystem, KanbanSystemChanged, Organization}
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Success, Try}

class SystemUseCaseSpec
  extends AnyFreeSpec {
  val organizationName = "Company"
  val firstOrganizationId: Id = "organization-1"

  "A System" - {
    "when empty" - {

      val system = KanbanSystem()

      "should not have any organization" in {
        SystemUseCase.query(GetAllOrganizations(system)) match {
          case Success(organizations: List[Organization]) => assert(organizations === List.empty)
          case _ => fail()
        }
      }

      "should be able to add an organization" in {
        SystemUseCase.change(AddOrganization(organizationName, system)) match {
          case Success(KanbanSystemChanged(newSystemState, organization: Organization)) =>
            assert(newSystemState.organizations.nonEmpty === true)
            assert(newSystemState.organizations.values.head.name === organizationName)
            assert(organization.name === organizationName)
          case _ => fail()
        }
      }
    }

    "when already have one organization" - {
      val system = KanbanSystem(initialState)

      "should be able to add an organization" in {
        SystemUseCase.change(AddOrganization("New Organization", system)) match {
          case Success(KanbanSystemChanged(newSystemState, organization)) =>
            assert(newSystemState.organizations.values.size === 2)
            assert(organization.name === "New Organization")
          case _ => fail()
        }
      }

      "should be able to search an organization by name" in {
        SystemUseCase.query(GetOrganizationByName(organizationName, system)) match {
          case Success(result) =>
            result match {
              case Some(organization: Organization) =>
                assert(organization.name === organizationName)
                assert(organization.id === firstOrganizationId)
              case None => fail()
            }
          case _ => fail()
        }
      }

      "should be able to search an organization by id" in {
        SystemUseCase.query(GetOrganizationById(firstOrganizationId, system)) match {
          case Success(result) =>
            result match {
              case Some(organization) =>
                assert(organization.name === organizationName)
                assert(organization.id === firstOrganizationId)
              case None => fail()
            }
          case _ => fail()
        }
      }

      "should be able to delete an organization by id" in {
        SystemUseCase.change(DeleteOrganization(firstOrganizationId, system)) match {
          case Success(KanbanSystemChanged(newSystemState, _)) =>
            assert(newSystemState.organizations === Map.empty)
          case _ => fail()
        }
      }
    }
  }

  private def initialState: Map[Id, Organization] =
    Map(
      (
        firstOrganizationId,
        Organization(id = firstOrganizationId, name = organizationName)
      )
    )
}
