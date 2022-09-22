package com.mikelalvarezgo.kamoot.infrastructure.marshaller

import com.mikelalvarezgo.kamoot.domain.Request
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase.NotifyNewUserCommand
import com.mikelalvarezgo.kamoot.modules.user.infrastructure.marshaller.NotifyNewUserCommandMarshaller._
import io.circe.Decoder
import io.circe.generic.extras.Configuration

object RequestMarshaller {

  object RequestMarshaller {
    implicit val marshallerConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

    implicit val requestReads: Decoder[Request] = { cursor =>
      cursor.as[NotifyNewUserCommand]
    }
  }
}
