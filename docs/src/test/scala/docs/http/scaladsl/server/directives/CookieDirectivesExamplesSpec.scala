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

package docs.http.scaladsl.server.directives

import org.apache.pekko
import pekko.http.scaladsl.server._
import pekko.http.scaladsl.model.headers.{ `Set-Cookie`, Cookie, HttpCookie }
import pekko.http.scaladsl.model.DateTime
import docs.CompileOnlySpec

class CookieDirectivesExamplesSpec extends RoutingSpec with CompileOnlySpec {
  "cookie" in {
    // #cookie
    val route =
      cookie("userName") { nameCookie =>
        complete(s"The logged in user is '${nameCookie.value}'")
      }

    // tests:
    Get("/") ~> Cookie("userName" -> "paul") ~> route ~> check {
      responseAs[String] shouldEqual "The logged in user is 'paul'"
    }
    // missing cookie
    Get("/") ~> route ~> check {
      rejection shouldEqual MissingCookieRejection("userName")
    }
    Get("/") ~> Route.seal(route) ~> check {
      responseAs[String] shouldEqual "Request is missing required cookie 'userName'"
    }
    // #cookie
  }
  "optionalCookie" in {
    // #optionalCookie
    val route =
      optionalCookie("userName") {
        case Some(nameCookie) => complete(s"The logged in user is '${nameCookie.value}'")
        case None             => complete("No user logged in")
      }

    // tests:
    Get("/") ~> Cookie("userName" -> "paul") ~> route ~> check {
      responseAs[String] shouldEqual "The logged in user is 'paul'"
    }
    Get("/") ~> route ~> check {
      responseAs[String] shouldEqual "No user logged in"
    }
    // #optionalCookie
  }
  "deleteCookie" in {
    // #deleteCookie
    val route =
      deleteCookie("userName") {
        complete("The user was logged out")
      }

    // tests:
    Get("/") ~> route ~> check {
      responseAs[String] shouldEqual "The user was logged out"
      header[`Set-Cookie`] shouldEqual Some(`Set-Cookie`(HttpCookie("userName", value = "deleted",
        expires = Some(DateTime.MinValue))))
    }
    // #deleteCookie
  }
  "setCookie" in {
    // #setCookie
    val route =
      setCookie(HttpCookie("userName", value = "paul")) {
        complete("The user was logged in")
      }

    // tests:
    Get("/") ~> route ~> check {
      responseAs[String] shouldEqual "The user was logged in"
      header[`Set-Cookie`] shouldEqual Some(`Set-Cookie`(HttpCookie("userName", value = "paul")))
    }
    // #setCookie
  }
}
