package tools

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait implicits {


  implicit val system = ActorSystem("Pokemon-api")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


}
