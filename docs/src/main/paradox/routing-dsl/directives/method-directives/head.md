# head

Matches requests with HTTP method `HEAD`.

@@@ div { .group-scala }

## Signature

@@signature [MethodDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MethodDirectives.scala) { #head }

@@@

## Description

This directive filters the incoming request by its HTTP method. Only requests with
method `HEAD` are passed on to the inner route. All others are rejected with a
@apidoc[MethodRejection], which is translated into a `405 Method Not Allowed` response
by the default @ref[RejectionHandler](../../rejections.md#the-rejectionhandler).

@@@ note
Apache Pekko HTTP can handle HEAD requests transparently by dispatching a GET request to the handler and
stripping off the result body. See the `pekko.http.server.transparent-head-requests` setting for how to enable
this behavior.
@@@

## Example

Scala
:  @@snip [MethodDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MethodDirectivesExamplesSpec.scala) { #head-method }

Java
:  @@snip [MethodDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MethodDirectivesExamplesTest.java) { #head }
