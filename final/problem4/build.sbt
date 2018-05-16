lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "edu.sjsu.cs",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "problem4",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.13.5",
      "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % "test",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",
      "org.apache.bcel" % "bcel" % "6.2"
    )
  )
