package com.mikelalvarezgo.kamoot.modules.user.application

import cats.implicits._
import com.mikelalvarezgo.kamoot.domain.error.{InvalidDateError, Validation}
import com.mikelalvarezgo.kamoot.domain.error.Validation.Validation
import com.mikelalvarezgo.kamoot.domain.{Command, UseCase}
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase.NotifyNewUserCommand
import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}
import com.mikelalvarezgo.kamoot.modules.user.domain.service.UserNotifier
import org.joda.time.DateTime

import scala.util.Try

final class NotifyNewUserUseCase[P[_]](userNotifier: UserNotifier[P] ) extends UseCase[P,NotifyNewUserCommand]{
	override def execute(r: NotifyNewUserCommand): Validation[P[Unit]] = validate(r).map(userNotifier.execute)

	private def validate(command: NotifyNewUserCommand) = (
		UserId.fromString(command.userId),
		command.name.valid,
		Validation.fromTry(InvalidDateError(command.createdAt), Try(DateTime.parse(command.createdAt).getMillis))
	).mapN(User.apply)
}

object NotifyNewUserUseCase{
	final case class NotifyNewUserCommand(userId: String, createdAt: String, name: String) extends Command
}
