name := "csvtoparquet_spark2_3_4"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.3.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.jsoup" % "jsoup" % "1.8.3", // Parsing
  "com.lihaoyi" %% "requests" % "0.1.8", //  PUT/POST
  "com.lihaoyi" %% "ujson" % "0.7.1" // JSON in PUT/POST
)


libraryDependencies += "org.apache.logging.log4j" % "log4j-scala" % "11.0"