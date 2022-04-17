package twoGis.crawler.models

import play.api.libs.json.{Json, Writes}

case class UrlList(urls: List[String])

object UrlList{
  implicit val fmt = Json.format[UrlList]
}

sealed trait ParsingResult

case class SuccessfulParsing(url: String, title: String) extends ParsingResult
case class FailedParsing(url: String, error: Throwable) extends ParsingResult

object SuccessfulParsing{
  implicit val fmt = Json.format[SuccessfulParsing]
}

object FailedParsing{
  implicit val failedParsingWrites = new Writes[FailedParsing] {
    def writes(failedParsing: FailedParsing) = Json.obj(
      "url"  -> failedParsing.url,
      "error" -> failedParsing.error.toString
    )
  }
}

case class CrawlResult(successful: List[SuccessfulParsing], failures: List[FailedParsing])

object CrawlResult{
  implicit val writes = Json.writes[CrawlResult]
}
