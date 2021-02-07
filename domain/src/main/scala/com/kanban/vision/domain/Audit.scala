package com.kanban.vision.domain

import java.time.Instant

case class Audit(
                  created: Instant = Instant.now(),
                  updated: Option[Instant] = None
                )
