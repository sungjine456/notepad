name := "notepad"

version := "1.0"

lazy val `notepad` = (project in file(".")).enablePlugins(PlayScala)

resolvers ++= Seq("scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/",
  "Atlassian Releases" at "https://maven.atlassian.com/public/")

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  ehcache, ws, specs2 % Test, guice,
  "org.postgresql" % "postgresql" % "42.2.8",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.mohiva" %% "play-silhouette" % "6.1.1",
  "com.mohiva" %% "play-silhouette-persistence" % "6.1.1",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "6.1.1",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "6.1.1",
  "com.mohiva" %% "play-silhouette-testkit" % "6.1.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test")

unmanagedResourceDirectories in Test += baseDirectory(_ / "target/web/public/test").value
