package com.mikelalvarezgo.kamoot.infrastructure.dependency_injection

import com.mikelalvarezgo.kamoot.domain.{Command, Request}
import com.mikelalvarezgo.kamoot.domain.error.Validation.ValidationFutureT

trait Dispatcher[T[_], R <: Command] {
  type ==>[A, B] = PartialFunction[A, B]

  def dispatch: Request ==> ValidationFutureT[Unit]
}
