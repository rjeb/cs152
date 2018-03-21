import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "edu.sjsu.cs",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "midterm2",
    libraryDependencies ++= Seq(scalaTest % Test,
"org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0") 
  )
