package com.mikelalvarezgo.kamoot.infrastructure.circe

import io.circe.{Json, Printer}
import io.circe.generic.extras.Configuration
import io.circe.parser._

trait CirceImplicits {
  implicit val marshallerConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val printer: Printer                = Printer(dropNullValues = true, indent = "")

  implicit class JsonMarshaller(value: String) {
    def toJson: Json = parse(value).getOrElse(Json.Null)
  }
}
