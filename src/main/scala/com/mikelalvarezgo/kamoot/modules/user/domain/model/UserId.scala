package com.mikelalvarezgo.kamoot.modules.user.domain.model

import cats.data.Validated
import com.mikelalvarezgo.kamoot.domain.error.Validation.Validation
import com.mikelalvarezgo.kamoot.domain.error._
import com.mikelalvarezgo.kamoot.domain.model.Id
import com.mikelalvarezgo.kamoot.modules.user.domain.error.InvalidUserId

import java.util.UUID

case class UserId(value: UUID) extends Id(value)

object UserId {

	def create: UserId = UserId(UUID.randomUUID())

	def fromString(value: String): Validation[UserId] =
		Validated.condNel(
			Id.isValid(value),
			UserId(UUID.fromString(value)),
			InvalidUserId(value)
		)
}
