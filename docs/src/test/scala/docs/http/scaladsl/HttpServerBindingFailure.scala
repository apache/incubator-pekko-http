/*
 * Copyright (C) 2020-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package docs.http.scaladsl

import org.apache.pekko
import pekko.actor.ActorSystem
import pekko.http.scaladsl.Http
import pekko.http.scaladsl.Http.ServerBinding
import pekko.http.scaladsl.server.Directives._

import scala.concurrent.Future

object HttpServerBindingFailure {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    // needed for the future foreach in the end
    implicit val executionContext = system.dispatcher

    val handler = get {
      complete("Hello world!")
    }

    // let's say the OS won't allow us to bind to 80.
    val (host, port) = ("localhost", 80)
    val bindingFuture: Future[ServerBinding] =
      Http().newServerAt(host, port).bindFlow(handler)

    bindingFuture.failed.foreach { ex =>
      system.log.error(ex, "Failed to bind to {}:{}!", host, port)
    }
  }
}
