package twoGis.crawler.services

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import play.api.libs.ws.{WSClient, WSResponse}
import twoGis.crawler.models._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util._
import scala.util.control.NonFatal


@Singleton
class Crawler @Inject()(
                         val ws: WSClient
                       )(implicit ec: ExecutionContext){
  val browser: Browser = JsoupBrowser()

  def getTitles(urlList: UrlList) ={
    val titles: List[Future[ParsingResult]] = urlList.urls.map(getTitle)
    val futTitles = Future.sequence(titles)
    val successes = futTitles.map{ l => l.filter{_.isInstanceOf[SuccessfulParsing]}.map(_.asInstanceOf[SuccessfulParsing])}
    val failures = futTitles.map{_.filter{_.isInstanceOf[FailedParsing]}.map(_.asInstanceOf[FailedParsing])}

    for {
      s <- successes
      f <- failures
    } yield CrawlResult(s, f)
  }

  def getTitle(url: String): Future[ParsingResult] ={
    val req = ws.url(url)

    val response: Future[WSResponse]  = Try(req.get()) match {
      case Success(v) => v
      case Failure(t) => Future.failed(t)
    }

    response.map{ response =>
      SuccessfulParsing(url, browser.parseString(response.body).title)
    }.recover{
      case NonFatal(t) => FailedParsing(url, t)
    }
  }
}



