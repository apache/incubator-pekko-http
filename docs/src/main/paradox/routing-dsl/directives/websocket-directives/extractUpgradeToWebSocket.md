# extractUpgradeToWebSocket

@@@ div { .group-scala }

## Signature

@@signature [WebSocketDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/WebSocketDirectives.scala) { #extractUpgradeToWebSocket }

@@@

## Description

This directive is deprecated, see @ref[extractWebSocketUpgrade](extractWebSocketUpgrade.md) instead.

If you are looking for a building block for @ref[Custom Directives](../custom-directives.md) to provide the websocket upgrade information to the inner route,
we recommend using the @apidoc[WebSocketUpgrade] @ref[attribute](../../../common/http-model.md#attributes) instead:

Scala
:  @@snip [WebSocketDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/WebSocketDirectivesExamplesSpec.scala) { #extractWebSocketUpgrade }

Java
:  @@snip [WebSocketDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/WebSocketDirectivesExamplesTest.java) { #extractWebSocketUpgrade }
