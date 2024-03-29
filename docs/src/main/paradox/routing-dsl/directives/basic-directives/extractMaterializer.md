# extractMaterializer

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractMaterializer }

@@@

## Description

Extracts the @apidoc[Materializer] from the @apidoc[RequestContext], which can be useful when you want to run an
Apache Pekko Stream directly in your route.

See also @ref[withMaterializer](withMaterializer.md) to see how to customise the used materializer for specific inner routes.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractMaterializer-0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractMaterializer }
