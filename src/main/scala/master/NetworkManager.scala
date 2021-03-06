package master

import akka.actor._
import common._

object NetworkManager extends App {

  implicit val system = ActorSystem("NetworkManagerSystem")

  // The local actor
  val localActor = system.actorOf(Props[NetworkManagerActor], name = "NetworkManagerActor")

  // Austin, tx location (lat, long)
  val location = new Location(30.25, 97.75)

  // Start the action
  localActor ! Start(location)

}

class NetworkManagerActor extends Actor {

  // create the remote actor
  val selection = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:2552/user/RemoteManagerActor")
  var counter = 0

  def receive = {
    case Start(loc) =>
      println(s"""Starting: $loc""")
      selection ! Message("Hello from the LocalActor")
    case Message(msg) =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! Message("Hello back to you")
        counter += 1
      }
    case _ =>
      println("LocalActor got something unexpected.")
  }

}

