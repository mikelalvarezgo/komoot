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
  ): P[Unit] =
    for {
      users <- userCache.getAll()
      _     <- userCache.put(user)
      _     <- buildMessageAndSendMessage(user, users)
    } yield ()

  private def buildMessageAndSendMessage(user: User, newUsers: List[User]): P[Unit] = {
    val beginMessage = s"Hello ${user.name}, welcome to komoot"
    val endMessage   = " also joined recently"
    val (message, userIds) = newUsers match {
      case Nil         => (beginMessage, Nil)
      case user :: Nil => (s"$beginMessage, ${user.name}, $endMessage", List(user.id))
      case user1 :: user2 :: Nil =>
        (s"$beginMessage, ${user1.name},  ${user2.name},$endMessage", List(user1.id, user2.id))
      case l =>
        val users = scala.util.Random.shuffle(l).take(3)
        (
          s"$beginMessage, ${users.head.name},  ${users.tail.head.name}, ${users.last.name},$endMessage",
          users.map(_.id)
        )
    }
    notificationClient.sendNotification(user, message, userIds)

  }
}
