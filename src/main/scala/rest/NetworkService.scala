package rest

import akka.actor._
import spray.routing.HttpService

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class NetworkServiceActor extends Actor with NetworkService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing,
  // timeout handling or alternative handler registration
  def receive = runRoute(networkAPI)
}

// this trait defines our service behavior independently from the service actor
trait NetworkService extends HttpService {

  val networkAPI = {
    get {
      pathSingleSlash {
        complete(index)
      } ~
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
        } ~
        path("timeout") { ctx =>
          // we simply let the request drop to provoke a timeout
        } ~
        path("crash") { ctx =>
          sys.error("crash boom bang")
        }
    }
  }

  lazy val index =
    <html>
      <body>
        <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
        <p>Defined resources:</p>
        <ul>
          <li><a href="/networks">/networks</a></li>
          <li><a href="/timeout">/timeout</a></li>
          <li><a href="/crash">/crash</a></li>
        </ul>
      </body>
    </html>
}