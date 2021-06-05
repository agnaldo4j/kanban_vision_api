import sbtassembly.{Log4j2MergeStrategy, MergeStrategy}

val scalatestVersion = "3.2.9"
val zioVersion = "1.0.9"
val zioHttpVersion = "1.0.0.0-RC16+31-8467a6aa-SNAPSHOT" //TODO not valid to scala3
val catsVersion = "2.6.1"
val catsEffectsVersion = "3.1.1"

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

lazy val scalaTestDependency = Seq(
  "org.scalactic" %% "scalactic" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.scalatest" %% "scalatest-freespec" % scalatestVersion % "test"
)

lazy val zioDependency = Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
)

lazy val zioHttpDependency = Seq(
  "io.d11" %% "zhttp" % zioHttpVersion
)

lazy val catsDependency = Seq(
  "org.typelevel" %% "cats-core" % catsVersion
)

lazy val catsEffectsDependency = Seq(
  "org.typelevel" %% "cats-effect" % catsEffectsVersion
)

//lazy val zHttpProject = RootProject(uri("git://github.com/dream11/zio-http.git"))

lazy val domain = (project in file("domain"))
  .settings(
    name := "Domain"
  ).disablePlugins(AssemblyPlugin)

lazy val adapters = (project in file("adapters"))
  .dependsOn(domain)
  .settings(
    name := "Adapters"
  ).disablePlugins(AssemblyPlugin)

lazy val useCase = (project in file("usecase"))
  .dependsOn(domain, adapters)
  .settings(
    name := "UseCase",
    libraryDependencies ++= scalaTestDependency,
    libraryDependencies ++= zioDependency,
  ).disablePlugins(AssemblyPlugin)

lazy val catsUseCase = (project in file("cats-usecase"))
  .dependsOn(domain, adapters)
  .settings(
    name := "CatsUseCase",
    libraryDependencies ++= scalaTestDependency,
    libraryDependencies ++= catsDependency,
    libraryDependencies ++= catsEffectsDependency
  ).disablePlugins(AssemblyPlugin)

lazy val zioHttpRest = (project in file("zio-http-rest"))
  .dependsOn()
  .settings(
    name := "ZioHttpRest",
    resolvers +=
      "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
    libraryDependencies ++= zioHttpDependency
  ).disablePlugins(AssemblyPlugin)


lazy val kanbanVisionApi = (project in file("."))
  .aggregate(adapters, domain, useCase, catsUseCase, zioHttpRest)
  .settings(
    name := "kanban-vision-api",
  ).disablePlugins(AssemblyPlugin)