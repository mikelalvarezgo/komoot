import sbt._

object Dependencies {
  object Version {
    val akkaHttp        = "10.2.7"
    val akkaHttpCirce   = "1.37.0"
    val akkaHttpCors    = "1.1.2"
    val catsCore        = "2.6.1"
    val circe           = "0.14.1"
		val joda = "2.11.1"
    val mockito         = "1.16.46"
    val scalaLogging    = "3.9.4"
    val typeSafe    = "1.4.1"
		val scaffeine = "5.2.0"
    val scalaTest       = "3.2.10"
    val sealerate       = "0.0.6"
    val slf4j           = "1.7.30"
  }

  object General {

    val circeExtras       = circe("circe-generic-extras")
    val circeGeneric      = circe("circe-generic")
    val circeParser       = circe("circe-parser")
    val circeLiteral      = circe("circe-literal")
		val scaffeine 				= "com.github.blemale" % "scaffeine_2.12" % "5.2.0"
    val akkaHttp          = "com.typesafe.akka" %% "akka-http"             % Version.akkaHttp
    val akkaHttpCirce     = "de.heikoseeberger" %% "akka-http-circe"       % Version.akkaHttpCirce
    val akkaHttpCors      = "ch.megard"         %% "akka-http-cors"        % Version.akkaHttpCors
    val cats              = "org.typelevel"     %% "cats-core"             % Version.catsCore
    val typeSafe          = "com.typesafe"       % "config"                % Version.typeSafe

    val joda = "joda-time" % "joda-time" % Version.joda

    // Logs
    val scalaLogging    = "com.typesafe.scala-logging" %% "scala-logging"            % Version.scalaLogging

  }

  object Testing {
    val scalaTest         = "org.scalatest"     %% "scalatest"         % Version.scalaTest
    val mockito           = "org.mockito"       %% "mockito-scala"     % Version.mockito
  }

  val commonDependencies = Seq(
    General.akkaHttp,
    General.akkaHttpCirce,
    General.akkaHttpCors,
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


  protected def circe(artifact: String): ModuleID    = "io.circe"              %% artifact % Version.circe
}
