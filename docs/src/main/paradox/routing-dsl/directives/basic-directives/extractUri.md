# extractUri

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractUri }

@@@

## Description

Access the full URI of the request.

Use @ref[SchemeDirectives](../scheme-directives/index.md), @ref[HostDirectives](../host-directives/index.md), @ref[PathDirectives](../path-directives/index.md),  and @ref[ParameterDirectives](../parameter-directives/index.md) for more
targeted access to parts of the URI.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractUri-example }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractUri }
