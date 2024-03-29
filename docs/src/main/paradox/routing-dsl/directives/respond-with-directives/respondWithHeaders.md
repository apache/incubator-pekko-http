# respondWithHeaders

@@@ div { .group-scala }

## Signature

@@signature [RespondWithDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RespondWithDirectives.scala) { #respondWithHeaders }

@@@

## Description

Adds the given HTTP headers to all responses coming back from its inner route.

This directive transforms @apidoc[HttpResponse] and `ChunkedResponseStart` messages coming back from its inner route by
adding the given @apidoc[HttpHeader] instances to the headers list.

See also @ref[respondWithHeader](respondWithHeader.md) if you'd like to add just a single header.

## Example

Scala
:  @@snip [RespondWithDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RespondWithDirectivesExamplesSpec.scala) { #respondWithHeaders-0 }

Java
:  @@snip [RespondWithDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RespondWithDirectivesExamplesTest.java) { #respondWithHeaders }
