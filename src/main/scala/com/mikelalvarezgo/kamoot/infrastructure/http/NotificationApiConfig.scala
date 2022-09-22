package com.mikelalvarezgo.kamoot.infrastructure.http

import com.typesafe.config.Config

final case class NotificationApiConfig(
  baseUri: String
)

object NotificationApiConfig {
  def fromConfig(config: Config) =
    NotificationApiConfig(
      config.getString("base-uri")
    )
}
