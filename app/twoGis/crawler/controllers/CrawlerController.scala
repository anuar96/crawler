package twoGis.crawler.controllers

import play.api.libs.json._
import play.api.mvc._
import twoGis.crawler.models._
import twoGis.crawler.services.Crawler

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CrawlerController @Inject()(
                                   cc: ControllerComponents,
                                   crawler: Crawler
                                 )(implicit ec: ExecutionContext) extends AbstractController(cc) {

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
