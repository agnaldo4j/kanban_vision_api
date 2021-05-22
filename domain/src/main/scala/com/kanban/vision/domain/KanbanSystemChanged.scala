package com.kanban.vision.domain

case class KanbanSystemChanged[DOMAIN](kanbanSystem: KanbanSystem, changedValue: DOMAIN)
