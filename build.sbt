name := "notepad"

version := "1.0"

lazy val `notepad` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  ehcache, ws, specs2 % Test, guice,
  "org.postgresql" % "postgresql" % "42.2.5",
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP7" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test")

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
