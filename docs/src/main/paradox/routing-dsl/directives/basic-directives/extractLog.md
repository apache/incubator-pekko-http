# extractLog

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractLog }

@@@

## Description

Extracts a @apidoc[LoggingAdapter] from the request context which can be used for logging inside the route.

The `extractLog` directive is used for providing logging to routes, such that they don't have to depend on
closing over a logger provided in the class body.

See @ref[extract](extract.md) and @ref[Providing Values to Inner Routes](index.md#providedirectives) for an overview of similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extract0Log }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractLog }
