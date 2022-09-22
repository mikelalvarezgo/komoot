lazy val root = project
  .in(file("."))
  .settings(
    name := "kamoot",
    Configuration.commonSettings,
    libraryDependencies ++= Dependencies.commonDependencies
  )