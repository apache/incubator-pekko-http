# mapRouteResultPF

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapRouteResultPF }

@@@

## Description

*Partial Function* version of @ref[mapRouteResult](mapRouteResult.md).

Changes the message the inner route sends to the responder.

The `mapRouteResult` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to transform the
@ref[RouteResult](../../routes.md#routeresult) coming back from the inner route. It's similar to the @ref[mapRouteResult](mapRouteResult.md) directive but allows to
specify a partial function that doesn't have to handle all potential @apidoc[RouteResult] instances.

See @ref[Result Transformation Directives](index.md#result-transformation-directives) for similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapRouteResultPF }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapRouteResultPF }
