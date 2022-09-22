package com.mikelalvarezgo.kamoot.domain.error

import scala.concurrent.{ExecutionContext, Future}

import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import cats.implicits.catsSyntaxApplicativeId
import com.mikelalvarezgo.kamoot.domain.error.Validation.Validation

final case class ValidationErrorException(
  validationErrors: NonEmptyList[ValidationError]
) extends Exception {
  override def getMessage: String =
    (List(message) ++ fields.map { case (k, v) => s"$k=$v" }).mkString(", ")

  def message: String = "Validation went wrong"

  def fields: Map[String, String] =
    List(Some("errors" -> validationErrors.toList.mkString(","))).flatten.toMap
}

object ValidationErrorException {
  def convertToFuture[T](validation: Validation[T])(implicit ec: ExecutionContext): Future[T] =
    validation match {
      case Invalid(e) => Future.failed(ValidationErrorException(e))
      case Valid(a)   => a.pure[Future]
    }
}
