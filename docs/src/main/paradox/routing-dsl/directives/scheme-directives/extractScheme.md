# extractScheme

@@@ div { .group-scala }

## Signature

@@signature [SchemeDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SchemeDirectives.scala) { #extractScheme }

@@@

## Description

Extracts the Uri scheme (i.e. "`http`", "`https`", etc.) for an incoming request.

For rejecting a request if it doesn't match a specified scheme name, see the @ref[scheme](scheme.md) directive.

## Example

Scala
:  @@snip [SchemeDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SchemeDirectivesExamplesSpec.scala) { #example-1 }

Java
:  @@snip [SchemeDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SchemeDirectivesExamplesTest.java) { #extractScheme }
