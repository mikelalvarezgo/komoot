package com.mikelalvarezgo.kamoot.modules.user.stub

import scala.util.Random

import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}
import org.joda.time.DateTime

object UserStub {

  def random(
    userId: UserId = UserId.create,
    name: String = Random.nextString(10),
    createdAt: Long = DateTime.now().getMillis
  ): User = User(userId, name, createdAt)

}
