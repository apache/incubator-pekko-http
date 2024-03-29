# mapRequest

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapRequest }

@@@

## Description

Transforms the request before it is handled by the inner route.

The `mapRequest` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to transform a request before it
is handled by the inner route. Changing the `request.uri` parameter has no effect on path matching in the inner route
because the unmatched path is a separate field of the @apidoc[RequestContext] value which is passed into routes. To change
the unmatched path or other fields of the @apidoc[RequestContext] use the @ref[mapRequestContext](mapRequestContext.md) directive.

See @ref[Request Transforming Directives](index.md#request-transforming-directives) for an overview of similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapRequest0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapRequest }
