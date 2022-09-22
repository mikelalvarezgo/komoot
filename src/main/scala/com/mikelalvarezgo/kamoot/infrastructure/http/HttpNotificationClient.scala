package com.mikelalvarezgo.kamoot.infrastructure.http

import java.rmi.UnexpectedException
import scala.concurrent.{ExecutionContext, Future}

import akka.actor.ActorSystem
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, MediaTypes, Uri}
import com.mikelalvarezgo.kamoot.domain.client.NotificationClient
import com.mikelalvarezgo.kamoot.domain.contract.Logger
import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}
import com.mikelalvarezgo.kamoot.modules.user.infrastructure.marshaller.SendNotificationRequestMarshaller._
import io.circe.syntax.EncoderOps

final class HttpNotificationClient(notificationApiConfig: NotificationApiConfig)(
  implicit ec: ExecutionContext,
  logger: Logger,
  system: ActorSystem
) extends NotificationClient[Future] {

  override def sendNotification(user: User, message: String, recentUsersIds: List[UserId]): Future[Unit] = {

    val params = SendNotificationRequest("mikelalvarezgo@gmail.com", user.id.raw, message, recentUsersIds.map(_.raw))
    val entity = HttpEntity.apply(MediaTypes.`application/json`, params.asJson.spaces2)
    val request = HttpRequest(uri = Uri(s"${notificationApiConfig.baseUri}"))
      .withEntity(entity)

    Http()
      .singleRequest(request)
      .map { response =>
        response.status match {
          case StatusCodes.OK => ()
          case _ =>
            logger.warning(
              s"Error in sending notification, status: ${response.status}"
            )
            throw new UnexpectedException(
              s"Unexpected exception when sending notification,, status: ${response.status}"
            )
        }
      }
  }

}
