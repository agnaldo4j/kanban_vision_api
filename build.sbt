import sbtassembly.{Log4j2MergeStrategy, MergeStrategy}

ThisBuild / organization := "com.thelambdadev"
ThisBuild / scalaVersion := "3.0.0"
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

lazy val useCase = (project in file("usecase"))
  .dependsOn(domain, adapters)
  .settings(
    name := "UseCase",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.9",
      "org.scalatest" %% "scalatest" % "3.2.9" % "test",
      "org.scalatest" %% "scalatest-freespec" % "3.2.9" % "test"
    )
  ).disablePlugins(AssemblyPlugin)


lazy val kanbanVisionApi = (project in file("."))
  .aggregate(adapters, domain, useCase)
  .settings(
    name := "kanban-vision-api",
  ).disablePlugins(AssemblyPlugin)