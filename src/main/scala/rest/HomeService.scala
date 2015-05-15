package rest

import spray.routing.HttpService

trait HomeService extends HttpService {

  val homeRoute = {
    get {
      pathSingleSlash {
        complete(index)
      }~
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
          <li><a href="/users">/users</a></li>
          <li><a href="/timeout">/timeout</a></li>
          <li><a href="/crash">/crash</a></li>
        </ul>
      </body>
    </html>
}
