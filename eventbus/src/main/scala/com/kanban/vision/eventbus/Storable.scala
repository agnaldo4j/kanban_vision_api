package com.kanban.vision.eventbus

import com.kanban.vision.adapters.Storage
import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.StorableEvent.StorableEvent
import com.kanban.vision.domain.SystemQueryable.SystemQuery

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

  def apply(event: StorableEvent)

  def apply(event: SystemQuery)

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
