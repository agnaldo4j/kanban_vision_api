package com.kanban.vision.eventbus

import com.kanban.vision.adapters.Storage
import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.domain.StorableEvent.StorableEvent
import com.kanban.vision.domain.SystemQueryable.SystemQuery
import com.kanban.vision.eventbus.system.{SystemChangeable, SystemQueryable}

object EventBus {
  def apply(storage: Storage): EventBus = new EventBus(storage)
  def apply(): EventBus = new EventBus(new DefaultStorage())
}

class EventBus(val storage: Storage)
  extends Storable
    with SystemQueryable
    with SystemChangeable {
  override var systemState: KanbanSystem = storage.loadSystem().getOrElse(KanbanSystem())

  override def apply(event: StorableEvent): Unit = execute(event)
  override def apply(event: SystemQuery): Unit = execute(event)
}
