package com.mikelalvarezgo.kamoot

import scala.concurrent.Future

import akka.actor.ActorSystem
import com.github.matsluni.akkahttpspi.AkkaHttpClient
import com.mikelalvarezgo.kamoot.domain.client.NotificationClient
import com.mikelalvarezgo.kamoot.domain.contract.Logger
import com.mikelalvarezgo.kamoot.infrastructure.http.{HttpNotificationClient, NotificationApiConfig}
import com.mikelalvarezgo.kamoot.infrastructure.logger.ScalaLoggingLogger
import com.mikelalvarezgo.kamoot.infrastructure.marshaller.RequestMarshaller.RequestMarshaller.requestReads
import com.mikelalvarezgo.kamoot.modules.user.infrastructure.dependency_injection.UserContext
import com.typesafe.config.ConfigFactory
import software.amazon.awssdk.auth.credentials.{AwsBasicCredentials, StaticCredentialsProvider}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

object Main extends App {

  implicit val logger: Logger = ScalaLoggingLogger("komoot-test-logger")
  lazy val config             = ConfigFactory.defaultApplication.getConfig("komoot-test")
  implicit val system         = ActorSystem.create("komoot-test")
  implicit val ec             = system.dispatcher
  implicit val notificationClient: NotificationClient[Future] =
    new HttpNotificationClient(NotificationApiConfig.fromConfig(config.getConfig("notification-api")))

  val credentialsProvider = StaticCredentialsProvider.create(
    AwsBasicCredentials.create("AKIA3DZULSSUR55ZUWFJ", "XOcRezqJMUEhZByd26S5O5AhQS+lbQsApUm2uptG")
  )

  implicit val awsSqsClient: SqsAsyncClient = SqsAsyncClient
    .builder()
    .credentialsProvider(credentialsProvider)
    .region(Region.US_EAST_2)
    .httpClient(AkkaHttpClient.builder().withActorSystem(system).build())
    .build()

  val userContext = new UserContext(config.getConfig("modules.user"))

  userContext.runConsumer()
}
