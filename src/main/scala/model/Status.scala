package model

import net.liftweb.json.Serialization.write
import net.liftweb.json._



object Status {

  case class PokemonDetails(name: String = "", like: Boolean = false, dislike: Boolean = false, comment: String = "")

  case class PokemonStatusResponse(name: String = "", like: Int = 0, dislike: Int = 0, comments: Seq[String] = Nil)

  def convertoJson(status: PokemonStatusResponse)= {
    implicit val formats = DefaultFormats
    write(status)
  }

}


