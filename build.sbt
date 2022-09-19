name := "KamootTest"

version := "0.1"

scalaVersion := "2.13.8"


lazy val root = project
  .in(file("."))
  .settings(
    name := "kamoot",
    Configuration.commonSettings,
    libraryDependencies ++= Dependencies.commonDependencies
  )