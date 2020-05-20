# play-template

This is a small play framework application one can start development from

## How to use it

- Create a repo in github
- Clone this repo to a needed folder 
```
git clone git@github.com:liveintent-berlin/play-template.git my-new-project
```
- Make origin point to the created repo
```
cd my-new-project
git remote set-url origin git@github.com:liveintent-berlin/my-new-project
```
- Replace all placeholders in the template files with values pertinent to your
  new project.
  
  The placeholders generally `<LOOK_LIKE_THIS>`.

  `<SCALA_APP_NAME>` determines the name of your application in the `sbt`
  build. This name is also used for the packaged startup script. Examples:
  `decision-engine-recommendation` or `streamliner`.
  
  `<EB_APP_NAME>` should match the Elastic Beanstalk Application where this
  service is going to be deployed. Examples: `DER` or `Streamliner`.
  
  `<AWS_SERVICE_TAG>` should match the `Service` tag set up for the EB
  application. In most cases this will be the same as `<EB_APP_NAME>`.
  
  `<SCALA_APP_NAME_WITHOUT_DASHES>` is the same as `<SCALA_APP_NAME>`
  with all funny characters removed - the `BuildInfo` class is auto-generated in a
  namespace derived from the sbt `name` setting and will strip away characters
  that are not legal in namespace identifiers. Examples:
  `decisionenginerecommendation` or `streamliner`.

- git push origin master
```
git add .
git commit -m "ready to use"
git push origin master
```
- Get a coffee

## If you have a compilation failure in Module.scala

- Comment the line with 
```scala
bind(classOf[BuildInfoProvider]).toInstance(new MirrorBuildInfoProvider(BuildInfo))
```
- Remove the import of `BuildInfo`
- Compile
- Add import as
```
import com.liveintent.realServiceName.BuildInfo
```
- Uncomment the line with
```scala
bind(classOf[BuildInfoProvider]).toInstance(new MirrorBuildInfoProvider(BuildInfo))
```
