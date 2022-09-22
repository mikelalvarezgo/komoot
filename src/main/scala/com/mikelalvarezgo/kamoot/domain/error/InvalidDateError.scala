package com.mikelalvarezgo.kamoot.domain.error

final case class InvalidDateError(value: String) extends ValidationError {
  override def message: String = s"Invalid date $value"
}
