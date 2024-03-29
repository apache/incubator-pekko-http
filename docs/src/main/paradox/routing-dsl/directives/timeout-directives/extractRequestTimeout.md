# extractRequestTimeout

@@@ div { .group-scala }

## Signature

@@signature [TimeoutDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/TimeoutDirectives.scala) { #extractRequestTimeout }

@@@

## Description

This directive extracts the currently set request timeout. 

@@@ warning
Please note that this extracts the request timeout at the current moment, but the timeout can be changed concurrently. 
See other timeout directives about raciness inherent to timeout directives. 
@@@

For more information about various timeouts in Apache Pekko HTTP see @ref[Pekko HTTP Timeouts](../../../common/timeouts.md).

## Example

Scala
:  @@snip [TimeoutDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/TimeoutDirectivesExamplesSpec.scala) { #extractRequestTimeout }

Java
:  @@snip [TimeoutDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/TimeoutDirectivesExamplesTest.java) { #extractRequestTimeout }
