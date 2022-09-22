package com.mikelalvarezgo.kamoot.domain

import com.mikelalvarezgo.kamoot.domain.error.Validation.Validation

trait Request {
  type Response
}

trait Command extends Request {
  override type Response = Unit
}

trait UseCase[T[_], R <: Request] {
  def execute(r: R): Validation[T[R#Response]]
}
