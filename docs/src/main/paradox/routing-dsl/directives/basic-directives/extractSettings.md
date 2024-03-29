# extractSettings

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #extractSettings }

@@@

## Description

Extracts the @apidoc[RoutingSettings] from the @apidoc[RequestContext].

By default the settings of the `Http()` extension running the route will be returned.
It is possible to override the settings for specific sub-routes by using the @ref[withSettings](withSettings.md) directive.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #extractSettings-examples }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #extractRequestContext }
