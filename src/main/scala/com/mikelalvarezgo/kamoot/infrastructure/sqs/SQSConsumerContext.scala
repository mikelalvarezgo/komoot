package com.mikelalvarezgo.kamoot.infrastructure.sqs

import scala.concurrent.{ExecutionContext, Future}

import akka.stream.{ActorAttributes, RestartSettings, Supervision}
import akka.stream.alpakka.sqs.SqsSourceSettings
import akka.stream.scaladsl.{Flow, RestartSource, Sink, Source}
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.alpakka.sqs.scaladsl.SqsSource
import com.mikelalvarezgo.kamoot.domain.{Command, Request}
import com.mikelalvarezgo.kamoot.domain.consumer.ConsumerContext
import com.mikelalvarezgo.kamoot.domain.contract.Logger
import com.mikelalvarezgo.kamoot.domain.error.{InvalidEventException, ValidationErrorException}
import com.mikelalvarezgo.kamoot.infrastructure.dependency_injection.Dispatcher
import io.circe.Decoder
import io.circe.parser._
import org.scalatest.time.SpanSugar.convertLongToGrainOfTime
import software.amazon.awssdk.auth.credentials.{AwsBasicCredentials, StaticCredentialsProvider}
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.Message

abstract class SqsConsumerContext[E <: Request]()(implicit awsSqsClient: SqsAsyncClient) extends ConsumerContext[E] {
  val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("x", "x"))

  val consumerConfig: SQSConsumerConfig

  def dispatcher: Dispatcher[Future, Command]

  override def runConsumer()(
    implicit logger: Logger,
    system: ActorSystem,
    ec: ExecutionContext,
    marshaller: Decoder[E]
  ): Future[Unit] = {
    def getSource: Source[Message, NotUsed] = {
      val supervisionStrategy: Supervision.Decider = Supervision.stoppingDecider
      val sourceSettings = SqsSourceSettings()
        .withWaitTime(consumerConfig.waitTime)
        .withMaxBufferSize(consumerConfig.maxBufferSize)
        .withMaxBatchSize(consumerConfig.maxBatchSize)

      val sqsSource = SqsSource(
        consumerConfig.topicArn,
        sourceSettings
      ).withAttributes(ActorAttributes.supervisionStrategy(supervisionStrategy))

      RestartSource.onFailuresWithBackoff(
        RestartSettings(
          minBackoff = consumerConfig.minBackoff.toMillis.millis,
          maxBackoff = consumerConfig.maxBackoff.toMillis.millis,
          randomFactor = consumerConfig.randomFactor
        )
      )(() => sqsSource)
    }

    def getFlow: Flow[Message, Unit, NotUsed] = {
      def processMessage(message: Message): Future[E] = {
        val event = parse(message.body()).flatMap(json => {
          val message = json.\\("Message").head.asString.get
          decode[E](message)
        })

        event match {
          case Left(_) =>
            Future.failed[E](
              new InvalidEventException(
                s"Invalid format of event from queue ${consumerConfig.topicArn}: ${message.body()}"
              )
            )
          case Right(value) => Future.successful(value)
        }
      }

      Flow[Message].mapAsyncUnordered(8) { message =>
        for {
          request <- processMessage(message)
          _       <- ValidationErrorException.convertToFuture(dispatcher.dispatch(request))
        } yield ()
      }
    }

    getSource
      .via(getFlow)
      .runWith(Sink.ignore)
      .map(_ => ())

  }
}
