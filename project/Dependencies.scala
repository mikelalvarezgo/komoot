import sbt._

object Dependencies {
  val commonDependencies: Seq[ModuleID] = Seq(
    General.akkaActor,
    General.akkaStream,
    General.akkaHttp,
    General.akkaSlf4j,
    General.akkaHttpCirce,
    General.akkaHttpCors,
    General.alpakkaSqs,
    General.cats,
    General.circeExtras,
    General.circeGeneric,
    General.circeLiteral,
    General.typeSafe,
    General.scalaLogging,
    General.scaffeine,
    General.joda,
    Testing.mockito,
    Testing.scalaTest
  )

  protected def circe(artifact: String): ModuleID = "io.circe" %% artifact % Version.circe

  object Version {
    val akka = "2.6.20"
    val akkaHttp = "10.2.10"
    val akkaHttpCirce = "1.39.2"
    val akkaHttpCors = "1.1.3"
    val alpakkaSqs = "4.0.0"
    val catsCore = "2.8.0"
    val circe = "0.14.1"
    val joda = "2.11.1"
    val mockito = "1.17.12"
    val scalaLogging = "3.9.5"
    val typeSafe = "1.4.2"
    val scaffeine = "5.2.1"
    val scalaTest = "3.2.12"

  }

  object General {

    val circeExtras = circe("circe-generic-extras")
    val circeGeneric = circe("circe-generic")
    val circeParser = circe("circe-parser")
    val circeLiteral = circe("circe-literal")
    val scaffeine = "com.github.blemale" %% "scaffeine" % Version.scaffeine
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akka
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka

    val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
    val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % Version.akkaHttpCirce
    val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
    val akkaHttpCors = "ch.megard" %% "akka-http-cors" % Version.akkaHttpCors
    val alpakkaSqs = "com.lightbend.akka" %% "akka-stream-alpakka-sqs" % Version.alpakkaSqs

    val cats = "org.typelevel" %% "cats-core" % Version.catsCore
    val typeSafe = "com.typesafe" % "config" % Version.typeSafe
    val joda = "joda-time" % "joda-time" % Version.joda

    // Logs
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging

  }

  object Testing {
    val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
    val mockito = "org.mockito" %% "mockito-scala" % Version.mockito
  }
}
