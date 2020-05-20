import com.google.inject.{AbstractModule, Provides, Singleton}
import com.liveintent.healthcheck.{DiskSpaceHealthCheck, DiskSpaceHealthCheckConfiguration, HealthChecker}
import com.liveintent.play.models.{BuildInfoProvider, InstanceInfoProvider, MirrorBuildInfoProvider}
import com.liveintent.play.services.{DummyInstanceInfoService, InstanceInfoService}
import com.liveintent.prometheus.MetricLogger
import com.liveintent.<SCALA_APP_NAME_WITHOUT_DASHES>.BuildInfo
import play.api.{Configuration, Environment, Mode}
import services.MetricService

class Module(env: Environment, conf: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    if (env.mode == Mode.Prod) {
      bind(classOf[InstanceInfoProvider]).to(classOf[InstanceInfoService])
    } else {
      bind(classOf[InstanceInfoProvider]).to(classOf[DummyInstanceInfoService])
    }

    bind(classOf[BuildInfoProvider]).toInstance(new MirrorBuildInfoProvider(BuildInfo))
    bind(classOf[MetricLogger]).to(classOf[MetricService]).asEagerSingleton()

    ()
  }

  @Provides
  @Singleton
  def healthChecker: HealthChecker = {
    new DiskSpaceHealthCheck(DiskSpaceHealthCheckConfiguration(conf.underlying))
  }
}
