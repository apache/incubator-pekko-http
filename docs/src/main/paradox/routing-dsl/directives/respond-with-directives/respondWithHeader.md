# respondWithHeader

@@@ div { .group-scala }

## Signature

@@signature [RespondWithDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RespondWithDirectives.scala) { #respondWithHeader }

@@@

## Description

Adds a given HTTP header to all responses coming back from its inner route.

This directive transforms @apidoc[HttpResponse] and `ChunkedResponseStart` messages coming back from its inner route by
adding the given @apidoc[HttpHeader] instance to the headers list.

See also @ref[respondWithHeaders](respondWithHeaders.md) if you'd like to add more than one header.

## Example

Scala
:  @@snip [RespondWithDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RespondWithDirectivesExamplesSpec.scala) { #respondWithHeader-0 }

Java
:  @@snip [RespondWithDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RespondWithDirectivesExamplesTest.java) { #respondWithHeader }
