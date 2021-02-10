import sbtassembly.{Log4j2MergeStrategy, MergeStrategy}

ThisBuild / organization := "com.thelambdadev"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / name         := "kanban-vision-api"
ThisBuild / javacOptions ++= Seq("-source", "1.11", "-target", "1.11")

val defaultMergeStrategy: String => MergeStrategy = {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList(ps @ _*) if ps.last == "Log4j2Plugins.dat" => Log4j2MergeStrategy.plugincache
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case x if x.contains("io.netty.versions.properties") => MergeStrategy.first
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.discard
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.discard
      case _ => MergeStrategy.deduplicate
    }
  case _ => MergeStrategy.deduplicate
}

lazy val domain = (project in file("domain"))
  .settings(
    name := "Domain"
  ).disablePlugins(AssemblyPlugin)

lazy val adapters = (project in file("adapters"))
  .dependsOn(domain)
  .settings(
    name := "Adapters",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.0",
    )
  ).disablePlugins(AssemblyPlugin)

lazy val config = (project in file("config"))
  .dependsOn(adapters)
  .settings(
    name := "Config",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.0",
    )
  ).disablePlugins(AssemblyPlugin)

lazy val useCase = (project in file("usecase"))
  .dependsOn(adapters)
  .settings(
    name := "UseCase",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.0",
      "org.scalatest" %% "scalatest" % "3.2.0" % "test",
      "org.scalatest" %% "scalatest-freespec" % "3.2.0" % "test"
    )
  ).disablePlugins(AssemblyPlugin)

lazy val eventBus = (project in file("eventbus"))
  .dependsOn(useCase)
  .settings(
    name := "EventBus",
  ).disablePlugins(AssemblyPlugin)

lazy val quillPersistence = (project in file("quill-persistence"))
  .dependsOn(config, eventBus)
  .settings(
    name := "QuillPersistence",
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % "42.2.17",
      "io.getquill" %% "quill-jdbc" % "3.5.3"
    ).map(_.exclude("org.slf4j", "*"))
  ).disablePlugins(AssemblyPlugin)

lazy val rest = (project in file("rest"))
  .dependsOn(quillPersistence)
  .settings(
    name := "Rest",
    assemblyMergeStrategy in assembly := defaultMergeStrategy,
    assemblyJarName in assembly := "kanban-vision-api.jar",
    test in assembly := {},
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core" % "0.32.1",
      "com.github.finagle" %% "finchx-circe" % "0.32.1",
      "io.circe" %% "circe-generic-extras" % "0.13.0",
    ),
    excludeDependencies ++= Seq(
      // commons-logging is replaced by jcl-over-slf4j
      ExclusionRule("commons-logging", "commons-logging")
    )
  )

lazy val kanbanVisionApi = (project in file("."))
  .aggregate(config, adapters, domain, quillPersistence, useCase, eventBus, rest)
  .settings(
    name := "kanban-vision-api",
  ).disablePlugins(AssemblyPlugin)