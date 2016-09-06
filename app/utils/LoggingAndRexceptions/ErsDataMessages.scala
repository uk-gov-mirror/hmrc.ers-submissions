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

package utils.LoggingAndRexceptions

import models.{ErsJsonStoreInfo, ErsSummary, SchemeInfo}

trait ErsDataMessages {

  val dataMessagePF: Seq[PartialFunction[Object, String]] = Seq(
    buildSchemeInfoMessage,
    buildErsJsonStoreInfoMessage,
    buildErsSummaryMessage,
    buildWildcardDataMessage
  )
  val buildDataMessage: PartialFunction[Object, String] = dataMessagePF.reduce(_ orElse _)

  def buildEmiterMessage: PartialFunction[Object, String] = {
    case data: Map[String @unchecked, String @unchecked] => s"${data.get("message").getOrElse("Undefined message")} in ${data.get("context").getOrElse("Undefined context")}"
  }

  def buildSchemeInfoMessage: PartialFunction[Object, String] = {
    case schemeInfo: SchemeInfo => s"SchemeInfo: ${schemeInfo.toString}"
  }

  def buildErsJsonStoreInfoMessage: PartialFunction[Object, String] = {
    case ersJsonStoreInfo: ErsJsonStoreInfo => s"FileId: ${ersJsonStoreInfo.fileId.getOrElse("Undefined fileId")},\n" +
      s"FileName: ${ersJsonStoreInfo.fileName.getOrElse("Undefined fileName")},\n" +
      s"FileLength: ${ersJsonStoreInfo.fileLength.getOrElse("Undefined fileLength")},\n" +
      s"UploadDate: ${ersJsonStoreInfo.uploadDate.getOrElse("Undefined uploadDate")},\n" +
      s"Status: ${ersJsonStoreInfo.status}, \n" +
      buildSchemeInfoMessage(ersJsonStoreInfo.schemeInfo)
  }

  def buildErsSummaryMessage: PartialFunction[Object, String] = {
    case ersSummary: ErsSummary => s"ConfirmationDateTime: ${ersSummary.confirmationDateTime.toString()}\n" +
      s"BundleRef: ${ersSummary.bundleRef},\n" +
      s"isNilReturn: ${ersSummary.isNilReturn},\n" +
      s"fileType: ${ersSummary.fileType.getOrElse("")},\n" +
      buildSchemeInfoMessage(ersSummary.metaData.schemeInfo)
  }

  def buildWildcardDataMessage: PartialFunction[Object, String] = {
    case data: Object => data.toString
  }

}
