/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config

import play.api.Play._
import uk.gov.hmrc.play.config.ServicesConfig
import scala.util.Try
import scala.collection.JavaConversions._

object ApplicationConfig extends ServicesConfig {

  private def loadConfig(key: String) = configuration.getString(key).getOrElse(throw new Exception(s"Missing key: $key"))

  lazy val presubmissionCollection = Try(loadConfig(s"$env.settings.presubmission-collection")).getOrElse("")
  lazy val metadataCollection = Try(loadConfig(s"$env.settings.metadata-collection")).getOrElse("")

  lazy val adrBaseURI: String = baseUrl("ers-stub")
  lazy val adrFullSubmissionURI: String = config("ers-stub").getString("full-submission-url").get
  lazy val UrlHeaderEnvironment: String = config("ers-stub").getString("environment").get
  lazy val UrlHeaderAuthorization: String = s"Bearer ${config("ers-stub").getString("authorization-token").get}"

  lazy val isSchedulerEnabled = Try(loadConfig(s"$env.scheduling.enabled").toBoolean).getOrElse(false)
  lazy val schedulerStatuses = Try(loadConfig(s"$env.scheduling.statuses").split(",").toList).getOrElse(List())
  lazy val schedulerRepeatIntervalInSeconds = Try(loadConfig(s"$env.scheduling.repeat-interval-sec").toInt).getOrElse(60)
  lazy val schedulerMaxRepeatIntervalInSeconds = Try(loadConfig(s"$env.scheduling.max-repeat-interval-sec").toInt).getOrElse(60)
  lazy val schedulerInitialDelayInMilliseconds = Try(loadConfig(s"$env.scheduling.initial-delay-ms").toInt).getOrElse(10000)
  lazy val schedulerMaxDelayInMilliseconds = Try(loadConfig(s"$env.scheduling.max-delay-ms").toInt).getOrElse(10000)
  lazy val schedulerStartHour = Try(loadConfig(s"$env.scheduling.start-hour").toInt).getOrElse(0)
  lazy val schedulerStartMinute = Try(loadConfig(s"$env.scheduling.start-minute").toInt).getOrElse(0)
  lazy val schedulerEndHour = Try(loadConfig(s"$env.scheduling.end-hour").toInt).getOrElse(0)
  lazy val schedulerEndMinute = Try(loadConfig(s"$env.scheduling.end-minute").toInt).getOrElse(0)
  lazy val schedulerLockExpireMin = Try(loadConfig(s"$env.scheduling.lock-expire-min").toInt).getOrElse(15)
  lazy val schedulerLockName = Try(loadConfig(s"$env.scheduling.lock-name")).getOrElse("ers-resubmission")

  lazy val schedulerSchemeRefListEnabled = Try(loadConfig(s"$env.scheduling.resubmit-list-enable").toBoolean).getOrElse(false)
  lazy val schedulerSchemeRefList: List[String] = Try(loadConfig(s"$env.scheduling.resubmit-list-schemeRefs").split(",").toList).getOrElse(List())
  lazy val schedulerSchemeRefStatusList: List[String] = Try(loadConfig(s"$env.scheduling.resubmit-list-statuses").split(",").toList).getOrElse(List())
  lazy val schedulerSchemeRefFailStatus: String = Try(loadConfig(s"$env.scheduling.resubmit-list-failStatus")).getOrElse("failedScheduler")
}