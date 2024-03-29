# mapInnerRoute

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapInnerRoute }

@@@

## Description

Changes the execution model of the inner route by wrapping it with arbitrary logic.

The `mapInnerRoute` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to replace the inner route
with any other route. Usually, the returned route wraps the original one with custom execution logic.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapInnerRoute }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapInnerRoute }
