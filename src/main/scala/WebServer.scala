import akka.http.scaladsl.Http
import routes.Routes
import tools.implicits
import tools.AppSettings._

import scala.io.StdIn

object WebServer extends implicits
  with Routes  {
  def main(args: Array[String]) {


    val bindingFuture = Http().bindAndHandle(route, hostname, port)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}