# headerValuePF

@@@ div { .group-scala }

## Signature

@@signature [HeaderDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/HeaderDirectives.scala) { #headerValuePF }

@@@

## Description

Calls the specified partial function with the first request header the function is `isDefinedAt` and extracts the
result of calling the function.

The `headerValuePF` directive is an alternative syntax version of @ref[headerValue](headerValue.md).

If the function throws an exception the request is rejected with a @apidoc[MalformedHeaderRejection].

If the function is not defined for any header the request is rejected as "NotFound".

## Example

Scala
:  @@snip [HeaderDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/HeaderDirectivesExamplesSpec.scala) { #headerValuePF-0 }

Java
:  @@snip [HeaderDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/HeaderDirectivesExamplesTest.java) { #headerValuePF }
