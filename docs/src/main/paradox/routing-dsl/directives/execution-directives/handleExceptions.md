# handleExceptions

@@@ div { .group-scala }

## Signature

@@signature [ExecutionDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/ExecutionDirectives.scala) { #handleExceptions }

@@@

## Description

Catches exceptions thrown by the inner route and handles them using the specified @apidoc[ExceptionHandler].

Using this directive is an alternative to using a global implicitly defined @apidoc[ExceptionHandler] that
applies to the complete route.

See @ref[Exception Handling](../../exception-handling.md) for general information about options for handling exceptions.

## Example

Scala
:  @@snip [ExecutionDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/ExecutionDirectivesExamplesSpec.scala) { #handleExceptions }

Java
:  @@snip [ExecutionDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/ExecutionDirectivesExamplesTest.java) { #handleExceptions }
