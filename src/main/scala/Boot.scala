import akka.actor.{Actor, Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import rest._
import spray.routing.HttpService

object Boot extends App {

  implicit val restSystem = ActorSystem("naas-rest-system")

  // Use Akka to create our Spray Service
  val restService = restSystem.actorOf(Props[APIServiceActor], "network-service")

  // Bind to Akka's I/O interface
  IO(Http) ! Http.Bind(
    restService,
    restSystem.settings.config.getString("app.interface"),
    restSystem.settings.config.getInt("app.port")
  )
}

// We don't implement our route structure directly in the service actor because
// We want to be able to test it independently, without having to spin up an actor
class APIServiceActor extends Actor with APIService {
  def actorRefFactory = context
  def receive = runRoute(APIRoutes)
}

// This trait defines our service behavior independently from the service actor
trait APIService extends HttpService
  with HomeService
  with NetworkService
  with UserService {
  val APIRoutes = homeRoute ~ networkAPI ~ userAPI
}