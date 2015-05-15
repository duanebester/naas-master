package rest

import akka.actor.Actor
import spray.routing.HttpService

class UserServiceActor extends Actor with UserService {
  def actorRefFactory = context
  def receive = runRoute(userAPI)
}

// this trait defines our service behavior independently from the service actor
trait UserService extends HttpService {

  val userAPI = {
    path("user") {
      put {
        complete("PUT USER!")
      } ~
      post {
        complete("POST USER!")
      } ~
      delete {
        complete("DELETE USER!")
      }
    } ~
    path("users") {
      get {
        complete("GET USERS!")
      }
    }
  }
}