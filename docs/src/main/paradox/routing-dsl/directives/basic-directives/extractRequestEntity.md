# extractRequestEntity

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractRequestEntity }

@@@

## Description

Extracts the @apidoc[RequestEntity] from the @apidoc[RequestContext].

The directive returns a @apidoc[RequestEntity] without unmarshalling the request. To extract domain entity,
@ref[entity](../marshalling-directives/entity.md) should be used.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractRequestEntity-example }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractRequestEntity }
