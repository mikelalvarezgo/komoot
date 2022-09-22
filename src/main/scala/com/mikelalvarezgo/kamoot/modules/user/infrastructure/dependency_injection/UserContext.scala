package com.mikelalvarezgo.kamoot.modules.user.infrastructure.dependency_injection

import scala.concurrent.{ExecutionContext, Future}

import cats.implicits._
import com.mikelalvarezgo.kamoot.domain.{Command, Request}
import com.mikelalvarezgo.kamoot.domain.client.NotificationClient
import com.mikelalvarezgo.kamoot.infrastructure.dependency_injection.Dispatcher
import com.mikelalvarezgo.kamoot.infrastructure.sqs.{SQSConsumerConfig, SqsConsumerContext}
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase
import com.mikelalvarezgo.kamoot.modules.user.domain.contract.UserCache
import com.mikelalvarezgo.kamoot.modules.user.domain.service.UserNotifier
import com.mikelalvarezgo.kamoot.modules.user.infrastructure.cache.ScaffeineUserCache
import com.typesafe.config.Config
import software.amazon.awssdk.services.sqs.SqsAsyncClient

final class UserContext(config: Config)(
  implicit ec: ExecutionContext,
  notificationClient: NotificationClient[Future],
  awsSqsClient: SqsAsyncClient
) extends SqsConsumerContext[Request] {

  override val consumerConfig: SQSConsumerConfig = SQSConsumerConfig.fromConfig(config.getConfig("consumer"))

  val userCache: UserCache[Future] = new ScaffeineUserCache[Future]()

  val userNotifier: UserNotifier[Future] = new UserNotifier[Future](userCache, notificationClient)

  val notifyNewUserUseCase = new NotifyNewUserUseCase[Future](userNotifier)

  override def dispatcher: Dispatcher[Future, Command] = new UserDispatcher(notifyNewUserUseCase)

}
