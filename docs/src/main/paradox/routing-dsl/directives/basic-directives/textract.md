@@@ div { .group-java }

The `textract` directive is not available in the Java API.

@@@

@@@ div { .group-scala }

# textract

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #textract }

## Description

Extracts a tuple of values from the request context and provides them to the inner route.

The `textract` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to extract data from the
@apidoc[RequestContext] and provide it to the inner route. To extract just one value use the @ref[extract](extract.md) directive. To
provide a constant value independent of the @apidoc[RequestContext] use the @ref[tprovide](tprovide.md) directive instead.

See @ref[Providing Values to Inner Routes](index.md#providedirectives) for an overview of similar directives.

See also @ref[extract](extract.md) for extracting a single value.

## Example

@@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #textract }

@@@
