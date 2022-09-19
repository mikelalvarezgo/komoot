package com.mikelalvarezgo.kamoot.utils

import cats.MonadError

object ThrowableTypeClasses {

  type MError[P[_]] = MonadError[P, Throwable]

  object MError {
    def apply[P[_]](implicit E: MonadError[P, Throwable]): MError[P] = E
  }
}
