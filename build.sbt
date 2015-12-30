scalaSource in Compile := baseDirectory.value / "src"
resourceDirectory in Compile := baseDirectory.value / "resources"

mainClass in (Compile, run) := Some("com.dongjindu.messageboard.MessageService")

val spraycan = "io.spray" % "spray-can_2.11" % "1.3.2"
val sprayrouting = "io.spray" % "spray-routing_2.11" % "1.3.2"
val sprayjson = "io.spray" % "spray-json_2.11" % "1.3.2"
val akka = "com.typesafe.akka" %% "akka-actor" % "2.3.6"
val javamail = "javax.mail" % "mail" % "1.4.7"
val javaxactivation = "javax.activation" % "activation" % "1.1.1"

lazy val commonSettings = Seq(
  organization := "com.dongjindu",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

//resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "hello",
    libraryDependencies += spraycan,
        libraryDependencies += sprayrouting,
        libraryDependencies += sprayjson,
        libraryDependencies += akka,
        libraryDependencies += javamail,
        libraryDependencies += javaxactivation,
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
//      libraryDependencies += "org.scala-lang" % "scala-xml" % scalaVersion.value,
        libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
        libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11.0-M6",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4"
    )
/* libraryDependencies ++= Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
        "org.scala-lang.modules" %% "scala-swing" % "1.0.1")
*/
                )


ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
