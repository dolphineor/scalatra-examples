import com.mojolly.scalate.ScalatePlugin.ScalateKeys._

organization := "com.github.dolphineor"
name := "scalatra-examples"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.7"

javacOptions ++= Seq(
  "-source", "1.8",
  "-target", "1.8",
  "-Xlint:unchecked"
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-unchecked"
)

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  "maven repo" at "https://repo1.maven.org/maven2/",
  "typesafe repo" at "https://repo.typesafe.com/typesafe/releases/",
  "scalaz repo" at "https://dl.bintray.com/scalaz/releases/",
  "sbt-plugin repo" at "https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"
)

libraryDependencies ++= {
  val ScalatraVersion = "2.4.0.RC3"
  val slickVersion = "3.1.0"
  Seq(
    "org.scalatra" %% "scalatra" % ScalatraVersion,
    "org.scalatra" %% "scalatra-auth" % ScalatraVersion,
    "org.scalatra" %% "scalatra-jetty" % ScalatraVersion,
    "org.scalatra" %% "scalatra-json" % ScalatraVersion,
    "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
    "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
    "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
    "com.typesafe" % "config" % "1.3.0",
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.zaxxer" % "HikariCP" % "2.4.1",
    "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
    "mysql" % "mysql-connector-java" % "5.1.37"
  )
}

enablePlugins(JettyPlugin)
containerLibs := Seq("org.eclipse.jetty" % "jetty-runner" % "9.3.5.v20151012" intransitive())
containerPort := 8081
containerConfigFile in Compile := Some(file("./src/main/webapp/WEB-INF/jetty-env.xml"))

scalateTemplateConfig in Compile <<= (sourceDirectory in Compile) { base =>
  Seq(
    TemplateConfig(
      base / "webapp" / "WEB-INF" / "templates",
      Seq.empty, /* default imports should be added here */
      Seq(
        Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
      ), /* add extra bindings here */
      Some("templates")
    )
  )
}