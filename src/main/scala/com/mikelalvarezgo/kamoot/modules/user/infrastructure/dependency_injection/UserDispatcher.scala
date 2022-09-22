package com.mikelalvarezgo.kamoot.modules.user.infrastructure.dependency_injection

import scala.concurrent.Future

import com.mikelalvarezgo.kamoot.domain.{Command, Request}
import com.mikelalvarezgo.kamoot.domain.error.Validation.ValidationFutureT
import com.mikelalvarezgo.kamoot.infrastructure.dependency_injection.Dispatcher
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase
import com.mikelalvarezgo.kamoot.modules.user.application.NotifyNewUserUseCase.NotifyNewUserCommand

final class UserDispatcher(notifyNewUserCommand: NotifyNewUserUseCase[Future]) extends Dispatcher[Future, Command] {

  override def dispatch: Request ==> ValidationFutureT[Unit] = {
    case req: NotifyNewUserCommand => notifyNewUserCommand.execute(req)
  }
}
