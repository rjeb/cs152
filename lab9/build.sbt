import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "lab9",
    libraryDependencies ++= Seq(scalaTest % Test,
"org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0")
  )
