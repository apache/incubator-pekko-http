# withMaterializer

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #withMaterializer }

@@@

## Description

Allows running an inner route using an alternative @apidoc[Materializer] in place of the default one.

The materializer can be extracted in an inner route using @ref[extractMaterializer](extractMaterializer.md) directly,
or used by directives which internally extract the materializer without surfacing this fact in the API
(e.g. responding with a Chunked entity).

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #withMaterializer-0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #withMaterializer }
