package routes

import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}
import model.Pokemon
import repositories.ElasticSearchRepository

trait Routes {

  val route =
    path("opinion") {
      post {entity(as[Pokemon]) { pokemon =>
        ElasticSearchRepository.saveToElastic(pokemon)
        complete(s"<h1>${pokemon.name} is saved</h1>")
      }
      }
    }

}
