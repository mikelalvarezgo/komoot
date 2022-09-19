package com.mikelalvarezgo.kamoot.domain.error

trait DomainError extends Throwable {
  def message: String

  override def getMessage: String = message
}
