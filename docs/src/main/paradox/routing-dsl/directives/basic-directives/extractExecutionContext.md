# extractExecutionContext

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractExecutionContext }

@@@

## Description

Extracts the `ExecutionContext` from the @apidoc[RequestContext].

See @ref[withExecutionContext](withExecutionContext.md) to see how to customise the execution context provided for an inner route.

See @ref[extract](extract.md) to learn more about how extractions work.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractExecutionContext-0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractExecutionContext }
