package com.mikelalvarezgo.kamoot.modules.user.infrastructure.marshaller

import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase.NotifyNewUserCommand
import io.circe.{Decoder, HCursor}

object NotifyNewUserCommandMarshaller {
  implicit val notifyNewUserCommand: Decoder[NotifyNewUserCommand] = (c: HCursor) =>
    for {
      name      <- c.downField("name").as[String]
      id        <- c.downField("id").as[Long]
      createdAt <- c.downField("created_at").as[String]
    } yield NotifyNewUserCommand(id, createdAt, name)
}
