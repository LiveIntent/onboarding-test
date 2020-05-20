resolvers ++= Seq(
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com",
  Resolver.url("bintray-kipsigman-sbt-plugins", url("http://dl.bintray.com/kipsigman/sbt-plugins"))(Resolver.ivyStylePatterns),
  "repo.jenkins-ci.org" at "http://repo.jenkins-ci.org/public",
  "Artifactory" at "https://liveintent.jfrog.io/liveintent/berlin/"
)

addSbtPlugin("com.liveintent" % "li-sbt-plugins" % "3.1.22")
// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.20")
