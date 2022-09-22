package com.mikelalvarezgo.kamoot.infrastructure.utils

import cats.Monad
import cats.data.OptionT
import cats.implicits._

object SyntaxUtils {

  implicit class OptionTSyntax[P[_]: Monad, A](value: OptionT[P, A]) {
    def flatFold[B](default: => B)(f: A => P[B]): P[B] = value.foldF(default.pure[P])(f)
  }

  implicit class OptionTOps[A](val value: A) {
    def someT[P[_]: Monad]: OptionT[P, A] = OptionT.some(value)

    def noneT[P[_]: Monad]: OptionT[P, A] = OptionT.none
  }

  implicit class OptionTTransformSyntax[P[_]: Monad, A, B](value: OptionT[P, A]) {
    def transform2(implicit convert: A => B): OptionT[P, B] = value.map(convert)
  }
}
