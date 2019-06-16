package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Pokemon(name: String = "", like: Boolean = false, dislike: Boolean = false, comment: String = "")


  object PokemonStatus extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val PortofolioFormats = jsonFormat4(Pokemon)
  }




