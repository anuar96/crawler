package twoGis.crawler.services

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import twoGis.crawler.models.UrlList

import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class Crawler @Inject()(){
  val browser = JsoupBrowser()

  def getTitles(urlList: UrlList): Map[String, String] ={
    urlList.urls.map{ url =>
      url -> browser.get(url).title
    }.toMap
  }
}



