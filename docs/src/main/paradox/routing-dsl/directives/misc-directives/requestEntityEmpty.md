# requestEntityEmpty

@@@ div { .group-scala }

## Signature

@@signature [MiscDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MiscDirectives.scala) { #requestEntityEmpty }

@@@

## Description

A filter that checks if the request entity is empty and only then passes processing to the inner route.
Otherwise, the request is rejected.

See also @ref[requestEntityPresent](requestEntityPresent.md) for the opposite effect.

## Example

Scala
:  @@snip [MiscDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MiscDirectivesExamplesSpec.scala) { #requestEntityEmptyPresent-example }

Java
:  @@snip [MiscDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MiscDirectivesExamplesTest.java) { #requestEntity-empty-present-example }
