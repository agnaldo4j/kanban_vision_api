package com.kanban.vision.eventbus

import com.kanban.vision.adapters.Storage
import com.kanban.vision.domain.{KanbanSystem, StorableEvent}

class DefaultStorage extends Storage {
  override def log(event: StorableEvent.StorableEvent): Unit = println("log")

  override def load(): List[StorableEvent.StorableEvent] = List()

  override def loadSystem(): Option[KanbanSystem] = Some(KanbanSystem())

  override def snapshot(kanbanSystem: KanbanSystem): Unit = println("snapshot")
}
