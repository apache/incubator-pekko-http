/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * license agreements; and to You under the Apache License, version 2.0:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * This file is part of the Apache Pekko project, derived from Akka.
 */

/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.http.scaladsl.testkit

import scala.concurrent.duration._
import org.apache.pekko
import pekko.actor.ActorSystem
import pekko.testkit._

case class RouteTestTimeout(duration: FiniteDuration)

object RouteTestTimeout {
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(1.second.dilated)
}
