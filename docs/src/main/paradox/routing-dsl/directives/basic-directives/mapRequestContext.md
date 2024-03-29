# mapRequestContext

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapRequestContext }

@@@

## Description

Transforms the @apidoc[RequestContext] before it is passed to the inner route.

The `mapRequestContext` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to transform
the request context before it is passed to the inner route. To change only the request value itself the
@ref[mapRequest](mapRequest.md) directive can be used instead.

See @ref[Request Transforming Directives](index.md#request-transforming-directives) for an overview of similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapRequestContext }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapRequestContext }
