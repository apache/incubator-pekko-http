# optionalHeaderValuePF

@@@ div { .group-scala }

## Signature

@@signature [HeaderDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/HeaderDirectives.scala) { #optionalHeaderValuePF }

@@@

## Description

Calls the specified partial function with the first request header the function is `isDefinedAt` and extracts the
result of calling the function.

The `optionalHeaderValuePF` directive is similar to the @ref[headerValuePF](headerValuePF.md) directive but always extracts an @scala[`Option`]@java[`Optional`]
value instead of rejecting the request if no matching header could be found.

## Example

Scala
:  @@snip [HeaderDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/HeaderDirectivesExamplesSpec.scala) { #optionalHeaderValuePF-0 }

Java
:  @@snip [HeaderDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/HeaderDirectivesExamplesTest.java) { #optionalHeaderValuePF }
