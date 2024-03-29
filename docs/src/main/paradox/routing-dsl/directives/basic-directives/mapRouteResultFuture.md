# mapRouteResultFuture

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapRouteResultFuture }

@@@

## Description

Asynchronous version of @ref[mapRouteResult](mapRouteResult.md).

It's similar to @ref[mapRouteResultWith](mapRouteResultWith.md), however it's
@scala[`Future[RouteResult] => Future[RouteResult]`]@java[`Function<CompletionStage<RouteResult>, CompletionStage<RouteResult>>`]
instead of
@scala[`RouteResult => Future[RouteResult]`]@java[`Function<RouteResult, CompletionStage<RouteResult>>`]
which may be useful when combining multiple transformations
and / or wanting to `recover` from a failed route result.

See @ref[Result Transformation Directives](index.md#result-transformation-directives) for similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapRouteResultFuture }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapRouteResultFuture }
