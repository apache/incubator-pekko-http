# reject

@@@ div { .group-scala }

## Signature

@@signature [RouteDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RouteDirectives.scala) { #reject }

@@@

## Description

Explicitly rejects the request optionally using the given rejection(s).

`reject` uses the given rejection instances (which might be the empty `Seq`) to construct a @scala[@scaladoc[Route](org.apache.pekko.http.scaladsl.server.index#Route=org.apache.pekko.http.scaladsl.server.RequestContext=%3Escala.concurrent.Future[org.apache.pekko.http.scaladsl.server.RouteResult])]@java[@javadoc[Route](org.apache.pekko.http.javadsl.server.Route)] which simply
calls `requestContext.reject`. See the chapter on @ref[Rejections](../../rejections.md) for more information on what this means.

After the request has been rejected at the respective point it will continue to flow through the routing structure in
the search for a route that is able to complete it.

The explicit `reject` directive is used mostly when building @ref[Custom Directives](../custom-directives.md), e.g. inside of a `flatMap`
modifier for "filtering out" certain cases.

## Example

Scala
:  @@snip [RouteDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RouteDirectivesExamplesSpec.scala) { #reject-examples }

Java
:  @@snip [RouteDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RouteDirectivesExamplesTest.java) { #reject }
