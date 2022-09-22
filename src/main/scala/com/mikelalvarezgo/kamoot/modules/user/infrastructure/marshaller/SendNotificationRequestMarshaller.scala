package com.mikelalvarezgo.kamoot.modules.user.infrastructure.marshaller

import com.mikelalvarezgo.kamoot.infrastructure.http.SendNotificationRequest
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.auto.exportEncoder
import io.circe.syntax.EncoderOps

object SendNotificationRequestMarshaller {
  implicit val marshallerConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit val encodeRequest: Encoder[SendNotificationRequest] = Encoder.instance {
    case r: SendNotificationRequest =>
      r.asJson(exportEncoder.instance)
  }
}
