# recoverRejections

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #recoverRejections }

@@@

## Description

**Low level directive** – unless you're sure you need to be working on this low-level you might instead
want to try the @ref[handleRejections](../execution-directives/handleRejections.md) directive which provides a nicer DSL for building rejection handlers.

Transforms rejections from the inner route with 
@scala[an `immutable.Seq[Rejection] => RouteResult` function.]
@java[a `Function<Iterable<Rejection>, RouteResult>`.]
A @apidoc[RouteResult] is either a
@scala[`Complete(HttpResponse(...))`]
@java[`Complete` containing the @apidoc[HttpResponse]]
or a
@scala[`Rejected(rejections)`.]
@java[`Rejected` containing the rejections.]

@@@ note
To learn more about how and why rejections work read the @ref[Rejections](../../rejections.md) section of the documentation.
@@@

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #recoverRejections }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #recoverRejections }
