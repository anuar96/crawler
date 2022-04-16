package twoGis.crawler.services

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import play.api.libs.ws.{StandaloneWSClient, WSClient}
import twoGis.crawler.models.UrlList

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class Crawler @Inject()(
                         val ws: WSClient
                       ){
  val browser: Browser = JsoupBrowser()

  def getTitles(urlList: UrlList): Future[Map[String, String]] ={
    val titles = urlList.urls.map{ url =>
      val req = ws.url(url)
      req.get().map{ response =>
        url -> browser.parseString(response.body).title
      }
    }
    Future.sequence(titles).map(_.toMap)
  }
}



