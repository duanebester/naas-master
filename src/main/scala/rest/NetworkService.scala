package rest

import akka.actor._
import spray.routing.HttpService

class NetworkServiceActor extends Actor with NetworkService {
  def actorRefFactory = context
  def receive = runRoute(networkAPI)
}

// this trait defines our service behavior independently from the service actor
trait NetworkService extends HttpService {

  val networkAPI = {
    path("network") {
      put {
        complete("PUT NETWORK!")
      } ~
      post {
        complete("POST NETWORK!")
      } ~
      delete {
        complete("DELETE NETWORK!")
      }
    } ~
    path("networks") {
      get {
        complete("GET NETWORKS!")
      }
    }
  }
}