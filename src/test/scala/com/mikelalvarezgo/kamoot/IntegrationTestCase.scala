package com.mikelalvarezgo.kamoot

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import com.mikelalvarezgo.kamoot.domain.contract.Logger
import com.typesafe.config.ConfigFactory
import org.scalatest.{EitherValues, OptionValues}
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

abstract class IntegrationTestCase
    extends AnyWordSpecLike
    with Matchers
    with OptionValues
    with ScalaFutures
    with Eventually
    with EitherValues {
  implicit override val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = 60.seconds, interval = 500.millis)
  val config = ConfigFactory.defaultApplication()

  implicit val logger: Logger               = DummyLogger
  implicit val system: ActorSystem          = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher
}
