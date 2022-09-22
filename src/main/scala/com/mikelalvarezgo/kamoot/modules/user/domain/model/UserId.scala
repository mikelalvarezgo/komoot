package com.mikelalvarezgo.kamoot.modules.user.domain.model

import scala.util.Random

import cats.data.Validated
import com.mikelalvarezgo.kamoot.domain.error.Validation.Validation
import com.mikelalvarezgo.kamoot.domain.model.Id
import com.mikelalvarezgo.kamoot.modules.user.domain.error.InvalidUserId

case class UserId(value: String) extends Id(value)

object UserId {

  def create: UserId = UserId(Random.nextInt(9999999).toString)

  def fromString(value: String): Validation[UserId] =
    Validated.condNel(
      Id.isValid(value),
      UserId(value),
      InvalidUserId(value)
    )
}
