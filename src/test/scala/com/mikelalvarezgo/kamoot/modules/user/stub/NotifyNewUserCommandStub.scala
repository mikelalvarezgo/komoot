package com.mikelalvarezgo.kamoot.modules.user.stub

import scala.util.Random

import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase.NotifyNewUserCommand
import com.mikelalvarezgo.kamoot.modules.user.domain.model.UserId
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

object NotifyNewUserCommandStub {

  def create(
    userId: Long = UserId.create.raw,
    name: String = Random.nextString(15),
    createdAt: String = DateTime.now().toString(ISODateTimeFormat.basicDateTime())
  ): NotifyNewUserCommand = NotifyNewUserCommand(userId, createdAt, name)

  def invalidDate(
    userId: Long = UserId.create.raw,
    name: String = Random.nextString(15),
    createdAt: String = "InvalidDate"
  ): NotifyNewUserCommand = NotifyNewUserCommand(userId, createdAt, name)
}
