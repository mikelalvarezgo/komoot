package com.mikelalvarezgo.kamoot.domain.client

import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}

trait NotificationClient[P[_]] {

  def sendNotification(user: User, message: String, recentUsersIds: List[UserId]): P[Unit]
}
