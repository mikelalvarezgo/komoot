package com.mikelalvarezgo.kamoot.domain.model

import java.util.UUID
import scala.util.Try

abstract class Id(value: UUID) {
  val raw: String = value.toString
}

object Id {
  def isValid(value: String): Boolean = Try(UUID.fromString(value)).isSuccess
  def genRaw: String                  = UUID.randomUUID().toString
}
