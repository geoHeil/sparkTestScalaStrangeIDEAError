name := "testingFailureIDEa"
organization := "tma"

scalaVersion := "2.11.8"


//The default SBT testing java options are too small to support running many of the tests
// due to the need to launch Spark in local mode.
fork := true
javaOptions ++= Seq("-Xms512M", "-Xmx2G",  "-XX:+CMSClassUnloadingEnabled", "-XX:+UseG1GC")
parallelExecution in Test := false

lazy val spark = "2.1.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % spark % "provided",
  "org.apache.spark" %% "spark-sql" % spark % "provided",
  "org.apache.spark" %% "spark-hive" % spark % "provided",
  "com.holdenkarau" % "spark-testing-base_2.11" % s"${spark}_0.6.0" % "test"
)

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in(Compile, run), runner in(Compile, run))

assemblyMergeStrategy in assembly := {
  case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}

test in assembly := {}