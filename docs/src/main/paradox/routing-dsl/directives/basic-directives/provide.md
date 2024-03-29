# provide

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #provide }

@@@

## Description

Provides a constant value to the inner route.

The *provide* directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to provide a single value to the
inner route.
@scala[To provide several values use the @ref[tprovide](tprovide.md) directive.]

See @ref[Providing Values to Inner Routes](index.md#providedirectives) for an overview of similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #provide0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #provide }
