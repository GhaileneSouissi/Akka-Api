package tools

import com.typesafe.config.ConfigFactory

object AppSettings {

  val appConfiguration = ConfigFactory.load()

  val hostname = appConfiguration.getString("server.hostname")
  val port = appConfiguration.getInt("server.port")

  object ElasticSearch {

    val hostname = appConfiguration.getString("elasticSearch.hostname")
    val port = appConfiguration.getInt("elasticSearch.port")
    val index = appConfiguration.getString("elasticSearch.index")
    val docType = appConfiguration.getString("elasticSearch.doc-type")
  }




}
