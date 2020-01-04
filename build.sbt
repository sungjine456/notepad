/**
 * SBT Shell 시작시 An illegal reflective access operation has occurred 경고 발생
 * 경고는 JDK 9 미만의 버전을 사용하거나 SBT를 1.3.* 버전으로 올리면 발생하지 않음
 * JDK 버전을 낮추는 것은 좋은 해결책이되지 않는다고 생각되며
 * SBT 버전을 1.3 이상의 버전으로 올릴 경우 불필요한 null 폴더가 생성되어 올리지 않음
 * 참고 : https://github.com/sbt/sbt/issues/5206
 * 또한 SBT 버전을 1.3 이상으로 버전을 컴파일시 guice 에서 동일한 경고가 발생
 * 참고 : https://github.com/google/guice/issues/1133
 */

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
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "io.github.nremond" %% "pbkdf2-scala" % "0.6.5",
  "com.mohiva" %% "play-silhouette" % "6.1.1",
  "com.mohiva" %% "play-silhouette-persistence" % "6.1.1",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "6.1.1",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "6.1.1",
  "com.mohiva" %% "play-silhouette-testkit" % "6.1.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test")

unmanagedResourceDirectories in Test += baseDirectory(_ / "target/web/public/test").value
