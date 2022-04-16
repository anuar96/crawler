name := "twoGisCrawler"
 
version := "1.0" 
      
lazy val `twogiscrawler` = (project in file(".")).enablePlugins(PlayScala)

      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.10"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
      