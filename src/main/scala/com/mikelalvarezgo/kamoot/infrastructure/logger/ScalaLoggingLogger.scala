package com.mikelalvarezgo.kamoot.infrastructure.logger

import com.mikelalvarezgo.kamoot.domain.contract.Logger
import com.typesafe.scalalogging.{Logger => ScalaLogging}

final case class ScalaLoggingLogger(name: String) extends Logger {
  private val logger: ScalaLogging = ScalaLogging(name)

  def info(msg: String, params: Map[String, Any]): Unit =
    logger.info(toMessage(msg, params))

  def info(msg: String, cause: Throwable): Unit = logger.info(msg, cause)

  def info(msg: String): Unit = logger.info(msg)

  def warning(msg: String, params: Map[String, Any]): Unit =
    logger.warn(toMessage(msg, params))

  private def toMessage(msg: String, params: Map[String, Any]): String =
    msg + ": " + params.map { case (k, v) => s"$k=$v" }.mkString(", ")

  def warning(msg: String, cause: Throwable): Unit = logger.warn(msg, cause)

  def warning(msg: String): Unit = logger.warn(msg)

  def error(msg: String, params: Map[String, Any]): Unit =
    logger.error(toMessage(msg, params))

  def error(msg: String, cause: Throwable): Unit = logger.error(msg, cause)

  def error(msg: String): Unit = logger.error(msg)

  def debug(msg: String, params: Map[String, Any]): Unit =
    logger.debug(toMessage(msg, params))

  def debug(msg: String, cause: Throwable): Unit = logger.debug(msg, cause)
}
