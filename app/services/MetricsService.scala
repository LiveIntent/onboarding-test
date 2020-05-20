package services

import akka.actor.ActorSystem
import com.liveintent.play.services.{JvmMetricsLogging, PlayMetrics}
import com.liveintent.prometheus._
import javax.inject.{Inject, Singleton}
import play.api.Configuration

trait MetricProvider
  extends MetricLogger
    with PlayMetrics

@Singleton
class MetricService @Inject()(implicit val playConfig: Configuration, val as: ActorSystem)
  extends PrometheusMetricLogger
    with MetricProvider
    with JvmMetricsLogging
