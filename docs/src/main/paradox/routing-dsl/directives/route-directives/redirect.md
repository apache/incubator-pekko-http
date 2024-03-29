# redirect

@@@ div { .group-scala }

## Signature

@@signature [RouteDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RouteDirectives.scala) { #redirect }

@@@

## Description

Completes the request with a redirection response to a given target URI and of a given redirection type (status code).

`redirect` is a convenience helper for completing the request with a redirection response.
It is equivalent to this snippet relying on the `complete` method on @apidoc[RequestContext], and a directive is also available:

Scala
:   @@snip [RequestContextImpl.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/RequestContextImpl.scala) { #red-impl }

Java
:  @@snip [RouteDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RouteDirectivesExamplesTest.java) { #red-impl }

## Example

Scala
:  @@snip [RouteDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RouteDirectivesExamplesSpec.scala) { #redirect-examples }

Java
:  @@snip [RouteDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RouteDirectivesExamplesTest.java) { #redirect }
