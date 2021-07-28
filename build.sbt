import sbtassembly.{Log4j2MergeStrategy, MergeStrategy}

val scalatestVersion = "3.2.9"
val zioVersion = "2.0.0-M1"
val zioIteropCats = "3.1.1.0"
val http4sVersion = "1.0.0-M23"
val catsVersion = "2.6.1"
val catsEffectsVersion = "3.2.0"

ThisBuild / organization := "com.thelambdadev"
ThisBuild / scalaVersion := "3.0.1"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / name         := "kanban-vision-api"
ThisBuild / javacOptions ++= Seq("-source", "1.13", "-target", "1.13")

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

lazy val zioTestDependency = Seq(
  "dev.zio" %% "zio-test" % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test",
)

lazy val zioDependency = Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-logging" % "0.5.11",
)

lazy val catsDependency = Seq(
  "org.typelevel" %% "cats-core" % catsVersion
)

lazy val catsEffectsDependency = Seq(
  "org.typelevel" %% "cats-effect" % catsEffectsVersion
)

lazy val http4sDependency = Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "dev.zio"    %% "zio-interop-cats"    % zioIteropCats
)

//lazy val zioMicroServicesProject = RootProject(uri("git://github.com/zio/zio-microservice.git"))

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
    libraryDependencies ++= zioTestDependency,
    libraryDependencies ++= zioDependency,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  ).disablePlugins(AssemblyPlugin)

lazy val catsUseCase = (project in file("cats-usecase"))
  .dependsOn(domain, adapters)
  .settings(
    name := "CatsUseCase",
    //libraryDependencies ++= scalaTestDependency,
    libraryDependencies ++= catsDependency,
    libraryDependencies ++= catsEffectsDependency
  ).disablePlugins(AssemblyPlugin)

//lazy val zioMicroservicesRest = (project in file("zio-microservices-rest"))
//  .dependsOn(zioMicroServicesProject)
//  .settings(
//    name := "ZioMicroservicesRest",
//    //scalacOptions += "-Ytasty-reader",
//    //libraryDependencies ++= zioDependency,
//  ).disablePlugins(AssemblyPlugin)

lazy val http4sRest = (project in file("http4s-rest"))
  .dependsOn()
  .settings(
    name := "http4sRest",
    libraryDependencies ++= http4sDependency
  ).disablePlugins(AssemblyPlugin)


lazy val kanbanVisionApi = (project in file("."))
  .aggregate(adapters, domain, useCase, catsUseCase)
  .settings(
    name := "kanban-vision-api",
  ).disablePlugins(AssemblyPlugin)