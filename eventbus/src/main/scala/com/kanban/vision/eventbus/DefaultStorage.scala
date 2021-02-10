package com.kanban.vision.eventbus

import com.kanban.vision.domain.KanbanSystem

class DefaultStorage extends Storage {
  override def log(event: EventBusCommand.Command): Unit = println("log")

  override def load(): List[EventBusCommand.Command] = List()

  override def loadSystem(): Option[KanbanSystem] = Some(KanbanSystem())

  override def snapshot(kanbanSystem: KanbanSystem): Unit = println("snapshot")
}
