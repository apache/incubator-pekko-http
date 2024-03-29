# optionalHeaderValueByName

@@@ div { .group-scala }

## Signature

@@signature [HeaderDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/HeaderDirectives.scala) { #optionalHeaderValueByName }

@@@

## Description

Optionally extracts the value of the HTTP request header with the given name.

The `optionalHeaderValueByName` directive is similar to the @ref[headerValueByName](headerValueByName.md) directive but always extracts
an @scala[`Option`]@java[`Optional`] value instead of rejecting the request if no matching header could be found.

## Example

Scala
:  @@snip [HeaderDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/HeaderDirectivesExamplesSpec.scala) { #optionalHeaderValueByName-0 }

Java
:  @@snip [HeaderDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/HeaderDirectivesExamplesTest.java) { #optionalHeaderValueByName }
