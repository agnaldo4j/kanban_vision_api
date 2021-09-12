package com.kanban.vision.usecase

import zio.test.*
import zio.test.environment.Live
import Assertion.isGreaterThan
import zio.Clock.nanoTime

val clockSuite = suite("clock")(
  test("time is non-zero") {
    assertM(Live.live(nanoTime))(isGreaterThan(0L))
  }
)

object SystemUseCaseZIOSpec extends DefaultRunnableSpec {
  def spec = suite("All tests")(clockSuite)
}