# withPrecompressedMediaTypeSupport

@@@ div { .group-scala }

## Signature

@@signature [CodingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/CodingDirectives.scala) { #withPrecompressedMediaTypeSupport }

@@@

## Description

Inspects the response entity and adds a `Content-Encoding: gzip` response header if
the entity's media-type is precompressed with gzip and no `Content-Encoding` header is present yet.

## Example

Scala
:  @@snip [CodingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/CodingDirectivesExamplesSpec.scala) { #withPrecompressedMediaTypeSupport }

Java
:  @@snip [CodingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/CodingDirectivesExamplesTest.java) { #withPrecompressedMediaTypeSupport }
