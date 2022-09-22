package com.mikelalvarezgo.kamoot.domain.error

final case class InvalidEventException(message: String) extends ValidationError
