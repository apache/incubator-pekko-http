# extractOfferedWsProtocols

@@@ div { .group-scala }

## Signature

@@signature [WebSocketDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/WebSocketDirectives.scala) { #extractOfferedWsProtocols }

@@@

## Description

Extracts the list of WebSocket subprotocols as offered by the client in the `Sec-WebSocket-Protocol` header if this is a WebSocket request. Rejects with an @apidoc[ExpectedWebSocketRequestRejection$], otherwise.

The `extractOfferedWsProtocols` directive is used as a building block for @ref[Custom Directives](../custom-directives.md) to provide the extracted protocols to the inner route.

## Example

Scala
:  @@snip [WebSocketDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/WebSocketDirectivesExamplesSpec.scala) { #extractOfferedWsProtocols }

Java
:  @@snip [WebSocketDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/WebSocketDirectivesExamplesTest.java) { #extractOfferedWsProtocols }
