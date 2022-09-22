import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
import sbt._
import sbt.Keys._

object Configuration {
  lazy val tb = taskKey[Unit]("Launch behaviour tests")
  lazy val ti = taskKey[Unit]("Launch integration tests")
  lazy val ta = taskKey[Unit]("Launch acceptance tests")

  val commonSettings = Seq(
    organization := "com.mikelalvarezgo",
    version := "1.0.0",
    scalaVersion := "2.13.8",
    Compile / doc / sources := Seq.empty,
    crossScalaVersions := Nil,
    Compile / packageDoc / publishArtifact := false,
    scalacOptions := {
      val default = Seq(
        "-Xfatal-warnings",
        "-Ywarn-unused",
        "-Ywarn-value-discard",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-Wconf:cat=deprecation:w",
        "-Wconf:cat=lint:w,cat=lint-byname-implicit:w",
        "-Wconf:cat=feature:i",
        "-Wconf:cat=unchecked:w",
        "-Wconf:cat=unused:w",
        "-Wconf:cat=w-flag-dead-code:w,cat=w-flag-value-discard:w"
      )
      if (version.value.endsWith("SNAPSHOT")) default :+ "-Xcheckinit" else default
      // check against early initialization
    },
    javaOptions := Seq(
      "-Duser.timezone=UTC",
      "-Xmx2048M",
      "-Xss2M"),
    // Scalafmt
    scalafmtConfig := file(".scalafmt.conf"),
  )

}
