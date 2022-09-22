package com.mikelalvarezgo.kamoot.modules.user.domain.error

import com.mikelalvarezgo.kamoot.domain.error.ValidationError

final case class InvalidUserId(value: String) extends ValidationError {
  override def message: String = s"Invalid userId $value"
}
