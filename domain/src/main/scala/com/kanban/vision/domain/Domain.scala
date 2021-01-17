package com.kanban.vision.domain

object Domain {
  type Id = String

  trait Domain {
    val id: Id
    val audit: Audit
  }

  trait OrderedDomain {
    val order: Int
  }

  trait ComparableByInteger extends Ordered[OrderedDomain] with OrderedDomain {
    override def compare(reference: OrderedDomain): Int = {
      reference.order match {
        case greater if (order < greater) => -1
        case less if (order > less) => 1
        case _ => 0
      }
    }
  }
}