package repositories
import akka.stream.alpakka.elasticsearch.WriteMessage
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchFlow
import akka.stream.scaladsl.{Sink, Source}
import model.Pokemon
import model.PokemonJsonSupport._
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import tools.implicits
import tools.AppSettings.ElasticSearch._

object ElasticSearchRepository extends implicits{


  implicit val client: RestClient = RestClient.builder(new HttpHost(hostname, port)).build()

  def saveToElastic(pokemon: Pokemon)= {
    val request = WriteMessage.createIndexMessage(id = pokemon.name, source = pokemon)

    Source(List(request))
      .via(
        ElasticsearchFlow.create[Pokemon](
          index,
          docType
        )
      )
      .runWith(Sink.seq)
  }


}
