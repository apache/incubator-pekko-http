# withSettings

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #withSettings }

@@@

## Description

Allows running an inner route using an alternative @apidoc[RoutingSettings] in place of the default one.

The execution context can be extracted in an inner route using @ref[extractSettings](extractSettings.md) directly,
or used by directives which internally extract the materializer without surfacing this fact in the API.

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #withSettings-0 }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #withSettings }
