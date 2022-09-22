package com.mikelalvarezgo.kamoot.domain.consumer

import scala.concurrent.{ExecutionContext, Future}

import akka.actor.ActorSystem
import com.mikelalvarezgo.kamoot.domain.contract.Logger
import io.circe.Decoder

abstract class ConsumerContext[E] {

  def runConsumer()(
    implicit logger: Logger,
    system: ActorSystem,
    ec: ExecutionContext,
    marshaller: Decoder[E]
  ): Future[Unit]
}
