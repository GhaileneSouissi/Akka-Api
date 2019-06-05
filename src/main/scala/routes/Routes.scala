package routes

import akka.http.scaladsl.server.Directives._
import model.Pokemon
import model.Status._
import repositories.ElasticSearchRepository

import scala.concurrent.Await
import scala.concurrent.duration._

trait Routes {


  val route =
    path("opinion") {
      post {entity(as[Pokemon]) { pokemon =>
        ElasticSearchRepository.saveToElastic(pokemon)
        complete(s"<h1>${pokemon.name} is saved</h1>")
      }
      }
    } ~
    path("read") {
      get {
        parameters('name.as[String]) { name =>
          Await.result(ElasticSearchRepository.readFromElastic(name), 5 seconds) match {
            case response => complete(convertoJson(response))
            case _ => complete("cannot find pokemon !! ")

          }

        }
      }
}
