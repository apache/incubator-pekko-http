# decodeRequestWith

@@@ div { .group-scala }

## Signature

@@signature [CodingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/CodingDirectives.scala) { #decodeRequestWith }

@@@

## Description

Decodes the incoming request if it is encoded with one of the given encoders.
If the request encoding doesn't match one of the given encoders the request is rejected with an @apidoc[UnsupportedRequestEncodingRejection]. If no decoders are given the default encoders (`Gzip`, `Deflate`, `NoCoding`) are used.
If the request entity after decoding exceeds `pekko.http.routing.decode-max-size` the stream fails with an
@scala[@scaladoc[EntityStreamSizeException](org.apache.pekko.http.scaladsl.model.EntityStreamSizeException)]@java[@javadoc[EntityStreamSizeException](org.apache.pekko.http.scaladsl.model.EntityStreamSizeException)].


## Example

Scala
:  @@snip [CodingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/CodingDirectivesExamplesSpec.scala) { #decodeRequestWith }

Java
:  @@snip [CodingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/CodingDirectivesExamplesTest.java) { #decodeRequestWith }
