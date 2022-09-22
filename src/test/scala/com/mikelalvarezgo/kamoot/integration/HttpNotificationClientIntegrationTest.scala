package com.mikelalvarezgo.kamoot.integration

import com.mikelalvarezgo.kamoot.IntegrationTestCase
import com.mikelalvarezgo.kamoot.infrastructure.http.{HttpNotificationClient, NotificationApiConfig}
import com.mikelalvarezgo.kamoot.modules.user.domain.model.{User, UserId}
import org.joda.time.DateTime

final class HttpNotificationClientIntegrationTest extends IntegrationTestCase {

  val client =
    new HttpNotificationClient(NotificationApiConfig("https://webhook.site/70dd6df9-1914-4901-8573-6a32def331fa"))

  "HttpNotificationClient" should {
    "send a notification when posting a request" in {
      val result = client.sendNotification(
        User(UserId.create, "mikel", DateTime.now().getMillis),
        "Test message!",
        List(UserId.create, UserId.create, UserId.create)
      )
      result.futureValue shouldBe ()
    }
  }
}
