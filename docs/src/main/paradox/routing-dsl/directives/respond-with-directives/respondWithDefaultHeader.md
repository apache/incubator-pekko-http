# respondWithDefaultHeader

@@@ div { .group-scala }

## Signature

@@signature [RespondWithDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RespondWithDirectives.scala) { #respondWithDefaultHeader }

@@@

## Description

Adds a given HTTP header to all responses coming back from its inner route only if a header with the same name doesn't
exist yet in the response.

This directive transforms @apidoc[HttpResponse] and `ChunkedResponseStart` messages coming back from its inner route by
potentially adding the given @apidoc[HttpHeader] instance to the headers list.
The header is only added if there is no header instance with the same name (case insensitively) already present in the
response.

See also @ref[respondWithDefaultHeaders](respondWithDefaultHeaders.md)  if you'd like to add more than one header.

## Example

Scala
:  @@snip [RespondWithDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RespondWithDirectivesExamplesSpec.scala) { #respondWithDefaultHeader-0 }

Java
:  @@snip [RespondWithDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RespondWithDirectivesExamplesTest.java) { #respondWithDefaultHeader }
