package com.kanban.vision.domain

import java.time.LocalDateTime

case class Audit(
                  created: LocalDateTime = LocalDateTime.now(),
                  updated: Option[LocalDateTime] = None
                )
