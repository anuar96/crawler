package twoGis.crawler.controllers

import play.api.libs.json._
import play.api.mvc._
import twoGis.crawler.models.UrlList
import twoGis.crawler.services.Crawler

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import scala.concurrent.Future

@Singleton
class CrawlerController @Inject()(
                                   cc: ControllerComponents,
                                   crawler: Crawler
                                 ) extends AbstractController(cc) {

  def getTitleFromUrls = Action.async(parse.json) { request =>
    val urls = request.body.validate[UrlList]

    urls.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      urls => {
        val titles = crawler.getTitles(urls)
        titles.map(x => Json.toJson(x)).map(Ok(_))
      }
    )
  }
}
