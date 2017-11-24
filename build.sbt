name := "cake"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.16"
libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.2.16"
libraryDependencies += "framboos" % "framboos" % "0.0.1-SNAPSHOT"