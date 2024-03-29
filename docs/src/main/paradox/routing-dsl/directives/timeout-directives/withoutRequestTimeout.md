# withoutRequestTimeout

@@@ div { .group-scala }

## Signature

@@signature [TimeoutDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/TimeoutDirectives.scala) { #withoutRequestTimeout }

@@@

## Description

This directive enables "late" (during request processing) control over the @ref[Request timeout](../../../common/timeouts.md#request-timeout) feature in Apache Pekko HTTP.

It is not recommended to turn off request timeouts using this method as it is inherently racy and disabling request timeouts
basically turns off the safety net against programming mistakes that it provides.

@@@ warning
Please note that setting the timeout from within a directive is inherently racy (as the "point in time from which
we're measuring the timeout" is already in the past (the moment we started handling the request), so if the existing
timeout already was triggered before your directive had the chance to change it, an timeout may still be logged.
@@@

For more information about various timeouts in Apache Pekko HTTP see @ref[Pekko HTTP Timeouts](../../../common/timeouts.md).

## Example

Scala
:  @@snip [TimeoutDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/TimeoutDirectivesExamplesSpec.scala) { #withoutRequestTimeout }

Java
:  @@snip [TimeoutDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/TimeoutDirectivesExamplesTest.java) { #withoutRequestTimeout-1 }
