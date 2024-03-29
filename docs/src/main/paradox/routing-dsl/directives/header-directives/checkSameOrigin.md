# checkSameOrigin

@@@ div { .group-scala }

## Signature

@@signature [HeaderDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/HeaderDirectives.scala) { #checkSameOrigin }

@@@

## Description

Checks that request comes from the same origin. Extracts the @apidoc[Origin] header value and verifies that allowed range
contains the obtained value. In the case of absent of the @apidoc[Origin] header rejects with a @apidoc[MissingHeaderRejection].
If the origin value is not in the allowed range rejects with an `InvalidOriginHeaderRejection`
and @scala[`StatusCodes.Forbidden`]@java[`StatusCodes.FORBIDDEN`] status.

## Example

Checking the @apidoc[Origin] header:

Scala
:  @@snip [HeaderDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/HeaderDirectivesExamplesSpec.scala) { #checkSameOrigin-0 }

Java
:  @@snip [HeaderDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/HeaderDirectivesExamplesTest.java) { #checkSameOrigin }
