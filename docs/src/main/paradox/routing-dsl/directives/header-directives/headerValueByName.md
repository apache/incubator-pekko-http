# headerValueByName

@@@ div { .group-scala }

## Signature

@@signature [HeaderDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/HeaderDirectives.scala) { #headerValueByName }

@@@

## Description

Extracts the value of the HTTP request header with the given name.

If no header with a matching name is found the request
is rejected with a @apidoc[MissingHeaderRejection].

If the header is expected to be missing in some cases or to customize
handling when the header is missing use the @ref[optionalHeaderValueByName](optionalHeaderValueByName.md) directive instead.

## Example

Scala
:  @@snip [HeaderDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/HeaderDirectivesExamplesSpec.scala) { #headerValueByName-0 }

Java
:  @@snip [HeaderDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/HeaderDirectivesExamplesTest.java) { #headerValueByName }
