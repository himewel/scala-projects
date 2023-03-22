ThisBuild / organization := "com.himewel"
name := "scala-projects"
scalaVersion := "3.2.0"

Compile / run / mainClass := Some("com.himewel.MainApp")

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "ch.qos.logback" % "logback-classic" % "1.2.10",
  "org.typelevel" %% "cats-core" % "2.9.0"
)
