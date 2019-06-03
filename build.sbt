name := "AkkaApi"

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.knoldus"

val akkaV = "2.4.5"
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.0.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.0.0",
  "com.typesafe.akka" %% "akka-http-core" % akkaV,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaV % "test",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.0-M3",
  "com.lightbend.akka" %% "akka-stream-alpakka-elasticsearch" % "1.0.2",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
  "net.liftweb" % "lift-json_2.11" % "2.6.2"

)