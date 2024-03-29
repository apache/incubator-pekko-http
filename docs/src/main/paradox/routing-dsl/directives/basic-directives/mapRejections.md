# mapRejections

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapRejections }

@@@

## Description

**Low level directive** – unless you're sure you need to be working on this low-level you might instead
want to try the @ref[handleRejections](../execution-directives/handleRejections.md) directive which provides a nicer DSL for building rejection handlers.

The `mapRejections` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to transform a list
of rejections from the inner route to a new list of rejections.

See @ref[Response Transforming Directives](index.md#response-transforming-directives) for similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapRejections }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapRejections }
