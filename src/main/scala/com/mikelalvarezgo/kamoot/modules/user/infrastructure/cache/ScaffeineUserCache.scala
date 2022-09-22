package com.mikelalvarezgo.kamoot.modules.user.infrastructure.cache

import scala.concurrent.duration._

import cats.implicits._
import cats.Applicative
import com.github.blemale.scaffeine.{Cache, Scaffeine}
import com.mikelalvarezgo.kamoot.modules.user.domain.contract.UserCache
import com.mikelalvarezgo.kamoot.modules.user.domain.model.User

final class ScaffeineUserCache[P[_]: Applicative] extends UserCache[P] {

  private val cache: Cache[String, User] =
    Scaffeine()
      .recordStats()
      .expireAfterWrite(2.hour)
      .maximumSize(200)
      .build[String, User]()

  override def put(user: User): P[Unit] =
    cache.put(user.id.value, user).pure[P]

  override def getAll(): P[List[User]] = cache.asMap().map(_._2).toList.pure[P]
}
