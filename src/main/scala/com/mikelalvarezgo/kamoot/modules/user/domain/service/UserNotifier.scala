package com.mikelalvarezgo.kamoot.modules.user.domain.service

import cats.Monad
import cats.implicits._
import com.mikelalvarezgo.kamoot.domain.client.NotificationClient
import com.mikelalvarezgo.kamoot.modules.user.domain.contract.UserCache
import com.mikelalvarezgo.kamoot.modules.user.domain.model.User

final class UserNotifier[P[_]: Monad](
										userCache: UserCache[P],
										notificationClient: NotificationClient[P]
														 ) {

	def execute(
						 user: User
						 ): P[Unit] = {
			userCache.get().flatMap(users => notificationClient.sendNotification(user, buildMessage(user, users)))

	}

	private def buildMessage(user: User, newUsers: List[User]) : String= {
		val beginMessage = s"Hello ${user.name}"
		newUsers match{
			case Nil => ""
			case user:: Nil =>
				case user1:: user2:: Nil =>
				case l => scala.util.Random.shuffle(l).take(3)
		}

	}
}
