package twoGis.crawler.models

import play.api.libs.json.Json

case class UrlList(urls: List[String])

object UrlList{
  implicit val fmt = Json.format[UrlList]
}