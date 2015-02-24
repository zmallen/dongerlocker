organization := "scala"

name := "DongerLocker"

version := "1.0"

scalaVersion := "2.10.2"

exportJars := true

retrieveManaged := true

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

resolvers += "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
