# responseEncodingAccepted

@@@ div { .group-scala }

## Signature

@@signature [CodingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/CodingDirectives.scala) { #responseEncodingAccepted }

@@@

## Description

Passes the request to the inner route if the request accepts the argument encoding. Otherwise, rejects the request with an `UnacceptedResponseEncodingRejection(encoding)`.

## Example

Scala
:  @@snip [CodingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/CodingDirectivesExamplesSpec.scala) { #responseEncodingAccepted }

Java
:  @@snip [CodingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/CodingDirectivesExamplesTest.java) { #responseEncodingAccepted }
