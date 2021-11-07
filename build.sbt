import sbtassembly.{Log4j2MergeStrategy, MergeStrategy}

val scalatestVersion = "3.2.10"
val zioVersion = "2.0.0-M4"
val zioLoggingVersion = "0.5.13"
val zioHttpVersion = "1.0.0.0-RC17"

ThisBuild / organization := "com.thelambdadev"
ThisBuild / scalaVersion := "3.1.0"
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
  "dev.zio" %% "zio-logging" % zioLoggingVersion,
)

//lazy val zioHttpDependency = Seq(
//  "io.d11" %% "zhttp"      % zioHttpVersion,
//  "io.d11" %% "zhttp-test" % zioHttpVersion % Test
//)

lazy val zioHttpProject = RootProject(uri("git://github.com/dream11/zio-http.git#release/next"))

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

lazy val zioHttpRest = (project in file("zioHttp-rest"))
  .dependsOn(zioHttpProject)
  .settings(
    name := "zioHttpRest",
    //libraryDependencies ++= zioHttpDependency
  ).disablePlugins(AssemblyPlugin)

lazy val kanbanVisionApi = (project in file("."))
  .aggregate(zioHttpRest, adapters, domain, useCase)
  .settings(
    name := "kanban-vision-api",
  ).disablePlugins(AssemblyPlugin)