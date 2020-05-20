import com.typesafe.sbt.packager.docker._

val LICommonVersion = "48.2.16"
val KamonPlayVersion = "1.1.1"

lazy val root = (project in file(".")).
  enablePlugins(PlayScala, LiveIntentPlugin, EBDeploymentPlugin).
  settings(LiveIntentPlugin.consoleSettings).
  settings(EBDeploymentPlugin.deploymentSettings).
  settings(
    name := "<SCALA_APP_NAME>",
    ebAppName := "<EB_APP_NAME>").
  settings(
    // Docker/Elastic Beanstalk
    maintainer in Docker := "Devs <dev-berlin@liveintent.com>",
    dockerExposedPorts := Seq(9000),
    dockerBaseImage := "openjdk:8-jdk-alpine",
    dockerPermissionStrategy := DockerPermissionStrategy.CopyChown,
    dockerLabels := Map(
      "version" -> (version in ThisBuild).value,
      "com.datadoghq.ad.logs" -> """[{\"source\": \"scala-app\", \"service\": \"<AWS_SERVICE_TAG>\"}]"""),
    dockerCommands := dockerCommands.value.flatMap {
      // Find the right place to insert additional commands...
      case cmd @ Cmd("COPY", args @ _*) =>
        // ...and append package installation commands
        Seq(Cmd("RUN", "apk add --no-cache bash"),
            Cmd("RUN", "apk add --no-cache tini"),
            cmd)
      case cmd @ ExecCmd("CMD", args @ _*) if args.isEmpty =>
        Seq(ExecCmd("CMD",
                    "bin/<SCALA_APP_NAME>",
                    "-J-javaagent:aspectj/aspectjweaver.jar",
                    "-Dstdout_log_format=json"))
      case cmd =>
        Seq(cmd)
    },
    dockerEntrypoint := Seq("/sbin/tini", "--")).
  settings(
    // Swagger deps
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      ehcache,
      guice,
      filters,
      "com.liveintent" %% "play-common" % LICommonVersion,
      "io.kamon" %% "kamon-play-2.6" % KamonPlayVersion,
      specs2 % Test
    ))
