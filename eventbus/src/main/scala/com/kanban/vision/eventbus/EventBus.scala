package com.kanban.vision.eventbus

import com.kanban.vision.domain.KanbanSystem
import EventBusCommand.Command
import EventBusQuery.Query
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

  override def apply(event: Command): Unit = execute(event)
  override def apply(event: Query): Unit = execute(event)
}
