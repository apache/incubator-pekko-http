# respondWithDefaultHeaders

@@@ div { .group-scala }

## Signature

@@signature [RespondWithDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/RespondWithDirectives.scala) { #respondWithDefaultHeaders }

@@@

## Description

Adds the given HTTP headers to all responses coming back from its inner route only if a respective header with the same
name doesn't exist yet in the response.

This directive transforms @apidoc[HttpResponse] and `ChunkedResponseStart` messages coming back from its inner route by
potentially adding the given @apidoc[HttpHeader] instances to the headers list.
A header is only added if there is no header instance with the same name (case insensitively) already present in the
response.

See also @ref[respondWithDefaultHeader](respondWithDefaultHeader.md) if you'd like to add only a single header.

## Example

The `respondWithDefaultHeaders` directive is equivalent to the `respondWithDefaultHeader` directive which
is shown in the example below, however it allows including multiple default headers at once in the directive, like so:

Scala
:   @@snip [RespondWithDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RespondWithDirectivesExamplesSpec.scala) { #multiple-headers }

Java
:   @@snip [RespondWithDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RespondWithDirectivesExamplesTest.java) { #multiple-headers }


The semantics remain the same however, as explained by the following example:

Scala
:   @@snip [RespondWithDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/RespondWithDirectivesExamplesSpec.scala) { #respondWithDefaultHeader-0 }

Java
:   @@snip [RespondWithDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/RespondWithDirectivesExamplesTest.java) { #respondWithDefaultHeaders }

See the @ref[respondWithDefaultHeader](respondWithDefaultHeader.md) directive for an example with only one header.