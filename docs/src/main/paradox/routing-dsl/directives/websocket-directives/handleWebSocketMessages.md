# handleWebSocketMessages

@@@ div { .group-scala }

## Signature

@@signature [WebSocketDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/WebSocketDirectives.scala) { #handleWebSocketMessages }

@@@

## Description

The directive first checks if the request was a valid WebSocket handshake request and if yes, it completes the request
with the passed handler. Otherwise, the request is rejected with an @apidoc[ExpectedWebSocketRequestRejection$].

WebSocket subprotocols offered in the `Sec-WebSocket-Protocol` header of the request are ignored. If you want to
support several protocols use the @ref[handleWebSocketMessagesForProtocol](handleWebSocketMessagesForProtocol.md) directive, instead.

For more information about the WebSocket support, see @ref[Server-Side WebSocket Support](../../../server-side/websocket-support.md).

## Example

Scala
:  @@snip [WebSocketDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/WebSocketDirectivesExamplesSpec.scala) { #greeter-service }

Java
:  @@snip [WebSocketDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/WebSocketDirectivesExamplesTest.java) { #handleWebSocketMessages }
