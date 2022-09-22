package com.mikelalvarezgo.kamoot.infrastructure.http

final case class SendNotificationRequest(sender: String, receiver: Long, message: String, recentUserIds: List[Long])
