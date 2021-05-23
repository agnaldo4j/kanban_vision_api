package com.kanban.vision.usecase

import com.kanban.vision.usecase.actions.{Changeable, Queryable}

object BoardUseCase extends Changeable with Queryable
