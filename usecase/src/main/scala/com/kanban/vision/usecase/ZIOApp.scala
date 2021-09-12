package com.kanban.vision.usecase

import com.kanban.vision.domain.{KanbanSystem, Organization}
import com.kanban.vision.domain.commands.SystemChangeable.AddOrganization
import zio.{Console, ZIO, Runtime}

object ZIOApp {

  val runtime = Runtime.default

  def addOrganizationLogic =
    for {
      systemChanged <- ZIO.fromTry(
        SystemUseCase.change[Organization](
          AddOrganization("new organization", KanbanSystem())
        )
      )
    } yield systemChanged.kanbanSystem

  @main
  def run() = {
    val newSystemState = runtime.unsafeRun(addOrganizationLogic)
    println(newSystemState)
  }
}
