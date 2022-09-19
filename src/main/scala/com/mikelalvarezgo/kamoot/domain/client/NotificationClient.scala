package com.mikelalvarezgo.kamoot.domain.client

import com.mikelalvarezgo.kamoot.modules.user.domain.model.User

trait NotificationClient[P[_]] {

	def sendNotification(user: User, message: String): P[Unit]
}
