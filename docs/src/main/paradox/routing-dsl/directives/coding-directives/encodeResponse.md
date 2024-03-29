# encodeResponse

@@@ div { .group-scala }

## Signature

@@signature [CodingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/CodingDirectives.scala) { #encodeResponse }

@@@

## Description

Encodes the response with the encoding that is requested by the client via the `Accept-Encoding` header or rejects the request with an `UnacceptedResponseEncodingRejection(supportedEncodings)`.

The response encoding is determined by the rules specified in [RFC7231](https://tools.ietf.org/html/rfc7231#section-5.3.4).

If the `Accept-Encoding` header is missing or empty or specifies an encoding other than identity, gzip or deflate then no encoding is used.

## Example

Scala
:  @@snip [CodingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/CodingDirectivesExamplesSpec.scala) { #encodeResponse }

Java
:  @@snip [CodingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/CodingDirectivesExamplesTest.java) { #encodeResponse }
