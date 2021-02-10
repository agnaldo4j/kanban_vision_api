package com.kanban.vision.adapters

import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.StorableEvent.StorableEvent

trait Storage {
  def log(event: StorableEvent)
  def load(): List[StorableEvent]
  def loadSystem(): Option[KanbanSystem]
  def snapshot(kanbanSystem: KanbanSystem)
}
