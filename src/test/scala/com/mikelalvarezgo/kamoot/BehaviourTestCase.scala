package com.mikelalvarezgo.kamoot

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.mikelalvarezgo.kamoot.domain.client.NotificationClient
import com.mikelalvarezgo.kamoot.domain.error.Validation.ValidationFutureT
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase
import com.mikelalvarezgo.kamoot.modules.user.domain.contract.UserCache
import com.mikelalvarezgo.kamoot.modules.user.domain.service.UserNotifier
import org.mockito.{ArgumentMatchersSugar, MockitoSugar}
import org.scalatest.{BeforeAndAfterAll, EitherValues, GivenWhenThen, OptionValues}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import org.scalatest.wordspec.AnyWordSpec

abstract class BehaviourTestCase
    extends AnyWordSpec
    with OptionValues
    with ScalaFutures
    with GivenWhenThen
    with EitherValues
    with BeforeAndAfterAll
    with Matchers
    with MockitoSugar
    with ArgumentMatchersSugar {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(2000, Millis)),
    interval = scaled(Span(100, Millis))
  )

  implicit class ValidationTestSyntax[T](validation: ValidationFutureT[T]) {
    def getResult: T = validation.toOption.get.futureValue

    def getFailure: Throwable = validation.toOption.get.failed.futureValue
  }

  //Infrastructure
  val client: NotificationClient[Future] = mock[NotificationClient[Future]]
  val userCache: UserCache[Future]       = mock[UserCache[Future]]

  //Services
  val userNotifier: UserNotifier[Future] = new UserNotifier[Future](userCache, client)

  //UseCases
  val notifyNewUserUseCase: NotifyNewUserUseCase[Future] = new NotifyNewUserUseCase[Future](userNotifier)
}
