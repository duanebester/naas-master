import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import rest._

object Boot extends App {
  implicit val system = ActorSystem("naas-rest-system")

  /* Use Akka to create our Spray Service */
  val service = system.actorOf(Props[NetworkServiceActor], "network-service")

  /* and bind to Akka's I/O interface */
  IO(Http) ! Http.Bind(service, system.settings.config.getString("app.interface"), system.settings.config.getInt("app.port"))
}