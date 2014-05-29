name := "porra"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "org.mindrot" % "jbcrypt" % "0.3m"
)     

play.Project.playScalaSettings
