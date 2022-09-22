package com.mikelalvarezgo.kamoot.domain.model

import scala.util.Try

abstract class Id(value: String) {
  def raw = value.toLong
}

object Id {
  def isValid(value: String): Boolean = Try(value.toLong).isSuccess
}
