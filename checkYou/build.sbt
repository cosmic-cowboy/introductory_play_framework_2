name := "checkYou"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  filters,
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" %% "webjars-play" % "2.2.2"
)

play.Project.playJavaSettings

resolvers += "webjars" at "http://webjars.github.com/m2"