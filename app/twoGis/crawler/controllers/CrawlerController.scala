package twoGis.crawler.controllers

import play.api.libs.json._
import play.api.mvc._
import twoGis.crawler.models.UrlList
import twoGis.crawler.services.Crawler

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CrawlerController @Inject()(
                                   cc: ControllerComponents,
                                   crawler: Crawler
                                 ) extends AbstractController(cc) {

  def getTitleFromUrls = Action(parse.json) { request =>
    val urls = request.body.validate[UrlList]

    urls.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      urls => {
        val titles = crawler.getTitles(urls)
        Ok(Json.toJson(titles))
      }
    )

  }

}
