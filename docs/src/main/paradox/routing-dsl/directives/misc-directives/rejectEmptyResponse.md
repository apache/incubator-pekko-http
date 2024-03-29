# rejectEmptyResponse

@@@ div { .group-scala }

## Signature

@@signature [MiscDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MiscDirectives.scala) { #rejectEmptyResponse }

@@@

## Description

Replaces a response with no content with an empty rejection.

The `rejectEmptyResponse` directive is mostly used together with marshallers that serialize to an empty response under
certain circumstances. @scala[For example serialization of `None`.]
In many cases this empty serialization should instead be handled as `404 Not Found` which is the effect of using `rejectEmptyResponse`.

## Example

Scala
:   @@snip [MiscDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MiscDirectivesExamplesSpec.scala) { #rejectEmptyResponse-example }

Java
:   @@snip [MiscDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MiscDirectivesExamplesTest.java) { #rejectEmptyResponse-example }
