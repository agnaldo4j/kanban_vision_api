package com.kanban.vision.eventbus

import com.kanban.vision.domain.KanbanSystem
import com.kanban.vision.eventbus.EventBusCommand.Command

trait Storage {
  def log(event: Command)

  def load(): List[Command]

  def loadSystem(): Option[KanbanSystem]

  def snapshot(kanbanSystem: KanbanSystem)
}
