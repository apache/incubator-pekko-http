# mapSettings

@@@ div { .group-scala }

## Signature

@@signature [BasicDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/BasicDirectives.scala) { #mapSettings }

@@@

## Description

Transforms the @apidoc[RoutingSettings] with a @scala[`RoutingSettings => RoutingSettings` function]@java[`Function<RoutingSettings, RoutingSettings>`].

See also @ref[withSettings](withSettings.md) or @ref[extractSettings](extractSettings.md).

## Example

Scala
:  @@snip [BasicDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/BasicDirectivesExamplesSpec.scala) { #mapSettings-examples }

Java
:  @@snip [BasicDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/BasicDirectivesExamplesTest.java) { #mapSettings }
