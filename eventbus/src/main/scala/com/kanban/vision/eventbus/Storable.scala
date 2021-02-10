package com.kanban.vision.eventbus

import EventBusCommand.Command
import EventBusQuery.Query
import com.kanban.vision.domain.KanbanSystem

trait Storable {
  var systemState: KanbanSystem
  val storage: Storage

  def reload() {
    reloadSystem()
    reloadEvents()
  }

  def snapshot() {
    storage.snapshot(systemState)
  }

  def apply(event: Command)

  def apply(event: Query)

  private def reloadSystem() {
    systemState = storage.loadSystem() match {
      case Some(loadedSystem) => loadedSystem
      case None               => KanbanSystem()
    }
  }

  private def reloadEvents() {
    val events = storage.load()
    events.foreach(apply)
  }
}
