package com.mikelalvarezgo.kamoot.modules.user.behaviour

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import cats.data.NonEmptyList
import cats.data.Validated.Invalid
import cats.implicits._
import com.mikelalvarezgo.kamoot.BehaviourTestCase
import com.mikelalvarezgo.kamoot.domain.error.InvalidDateError
import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}
import com.mikelalvarezgo.kamoot.modules.user.stub.{NotifyNewUserCommandStub, UserStub}
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

final class NotifyNewUserUseCaseTest extends BehaviourTestCase {

  "NotifyNewUserUseCase" when {
    "receive a valid command" should {
      "send a notification with users stored in cache" in {
        val now     = DateTime.now()
        val newUser = UserStub.random(createdAt = now.getMillis)
        val command = NotifyNewUserCommandStub.create(
          newUser.id.raw,
          newUser.name,
          now.toString(ISODateTimeFormat.dateHourMinuteSecond())
        )
        val cachedUsers: List[User] = (1 to 3).map(_ => UserStub.random()).toList
        val expectedMessage =
          s"Hello ${command.name}, welcome to komoot. ${cachedUsers.head.name}, ${cachedUsers.tail.head.name}, ${cachedUsers.last.name}, also joined recently"

        when(userCache.getAll()).thenReturn(cachedUsers.pure[Future])
        when(userCache.put(any[User])).thenReturn(().pure[Future])
        when(client.sendNotification(any[User], any[String], any[List[UserId]])).thenReturn(().pure[Future])

        val result = notifyNewUserUseCase.execute(command)
        result.isValid shouldBe true
        result.getResult shouldBe ()

        verify(client, times(1)).sendNotification(_, expectedMessage, _)

      }
    }
    "receive a not valid command" should {
      "return validation error" in {
        val command = NotifyNewUserCommandStub.invalidDate()

        val result = notifyNewUserUseCase.execute(command)
        result.isValid shouldBe false
        result shouldBe Invalid(NonEmptyList.one(InvalidDateError(command.createdAt)))

      }
    }
  }
}
