ThisBuild / organization := "com.himewel"
name := "scala-projects"
scalaVersion := "3.2.0"

Compile / run / mainClass := Some("com.himewel.MainApp")

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "ch.qos.logback" % "logback-classic" % "1.2.10"
)
