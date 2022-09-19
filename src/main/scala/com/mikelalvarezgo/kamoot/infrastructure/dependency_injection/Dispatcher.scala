package com.mikelalvarezgo.kamoot.infrastructure.dependency_injection

import com.mikelalvarezgo.quinielator.shared.domain.Request
import com.mikelalvarezgo.quinielator.shared.domain.error.Validation.ValidationFutureT

trait Dispatcher {
  type ==>[A, B] = PartialFunction[A, B]

  def dispatch: Request ==> ValidationFutureT[Unit]
}
