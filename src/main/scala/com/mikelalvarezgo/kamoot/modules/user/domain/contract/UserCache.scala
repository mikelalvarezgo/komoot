package com.mikelalvarezgo.kamoot.modules.user.domain.contract

import com.mikelalvarezgo.kamoot.modules.user.domain.model.User

trait UserCache[P[_]] {

  def put(user: User): P[Unit]

  def getAll(): P[List[User]]
}
