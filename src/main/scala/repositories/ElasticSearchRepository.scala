package repositories
import akka.stream.alpakka.elasticsearch.scaladsl.{ElasticsearchFlow, ElasticsearchSource}
import akka.stream.alpakka.elasticsearch.{ElasticsearchSourceSettings, ReadResult, WriteMessage}
import akka.stream.scaladsl.{Sink, Source}
import model.Pokemon
import model.PokemonJsonSupport._
import model.Status._
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import tools.AppSettings.ElasticSearch._
import tools.implicits

import scala.concurrent.Future

object ElasticSearchRepository extends implicits{


  implicit val client: RestClient = RestClient.builder(new HttpHost(hostname, port)).build()
  private val timestamp: String = System.currentTimeMillis.toString
  private val docType = "pokemon"



  def saveToElastic(pokemon: Pokemon)= {
    val index = s"Pokemon-${pokemon}"
    val request = WriteMessage.createIndexMessage(id = pokemon.name+"-"+timestamp, source = pokemon)


    Source(List(request))
      .via(
        ElasticsearchFlow.create[Pokemon](
          index,
          docType
        )
      )
      .runWith(Sink.seq)
  }

  def readFromElastic(pokemonName: String) = {
    val ESResponse = ElasticsearchSource
      .typed[Pokemon](
      s"Pokemon-${pokemonName}",
      docType,
      """{"match_all": {}}""",
      ElasticsearchSourceSettings()
    ).map { message: ReadResult[Pokemon] => message
    }
      .runWith(Sink.seq)

    ESResponse flatMap {
      responses =>
        val likes = responses.foldLeft(0,0) {
        (acc,num) =>
          val x = (num.source.dislike,num.source.like) match {
          case (true,true) => (1,1)
          case (true,false) => (1,0)
          case (false,true) => (0,1)
          case (false,false) => (0,0)
        }
          (acc._1 + x._1,acc._2 + x._2)
      }
        val comments = responses.map{response =>
          response.source.comment
        }

        val newPokemonStatus = PokemonStatusResponse(
          name = pokemonName,
          like = likes._2,
          dislike = likes._1,
          comments = comments
        )
        Future.successful(newPokemonStatus)
    }

  }



}
