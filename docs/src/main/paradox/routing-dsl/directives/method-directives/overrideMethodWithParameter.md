# overrideMethodWithParameter

@@@ div { .group-scala }
## Signature

@@signature [MethodDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MethodDirectives.scala) { #overrideMethodWithParameter }

@@@

## Description

Changes the request method to the value of the specified query parameter.

Changes the HTTP method of the request to the value of the specified query string parameter.
If the query string parameter is not specified this directive has no effect.

If the query string is specified as something that is not an HTTP method,
then this directive completes the request with a `501 Not Implemented` response.

This directive is useful for:

 * Use in combination with JSONP (JSONP only supports GET)
 * Supporting older browsers that lack support for certain HTTP methods. E.g. IE8 does not support PATCH

## Example

Scala
:  @@snip [MethodDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MethodDirectivesExamplesSpec.scala) { #overrideMethodWithParameter-0 }

Java
:  @@snip [MethodDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MethodDirectivesExamplesTest.java) { #overrideMethodWithParameter }
