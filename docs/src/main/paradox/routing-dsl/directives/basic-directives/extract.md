# extract

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extract }

@@@

## Description

The `extract` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to extract data from the
@apidoc[RequestContext] and provide it to the inner route.
@scala[It is a special case for extracting one value of the more general @ref[textract](textract.md) directive that can be used to extract more than one value.]

See @ref[Providing Values to Inner Routes](index.md#providedirectives) for an overview of similar directives.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extract0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extract }
