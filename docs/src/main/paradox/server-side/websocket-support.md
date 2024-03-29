# Server WebSocket Support

WebSocket is a protocol that provides a bi-directional channel between browser and webserver usually run over an
upgraded HTTP(S) connection. Data is exchanged in messages whereby a message can either be binary data or Unicode text.

Apache Pekko HTTP provides a stream-based implementation of the WebSocket protocol that hides the low-level details of the
underlying binary framing wire-protocol and provides a simple API to implement services using WebSocket.

## Model

The basic unit of data exchange in the WebSocket protocol is a message. A message can either be binary message,
i.e. a sequence of octets or a text message, i.e. a sequence of Unicode code points.

In the data model the two kinds of messages, binary and text messages, are represented by the two classes
@apidoc[BinaryMessage] and @apidoc[TextMessage] deriving from a common superclass
@scala[@scaladoc[Message](org.apache.pekko.http.scaladsl.model.ws.Message)]@java[@javadoc[Message](org.apache.pekko.http.javadsl.model.ws.Message)].
@scala[The subclasses @apidoc[BinaryMessage] and @apidoc[TextMessage] contain methods to access the data.]
@java[The superclass @javadoc[Message](org.apache.pekko.http.javadsl.model.ws.Message)]
contains `isText` and `isBinary` methods to distinguish a message and `asBinaryMessage` and `asTextMessage` methods to cast a message.]
Take the API of @apidoc[TextMessage] as an example (@apidoc[BinaryMessage] is very similar with `String` replaced by @apidoc[org.apache.pekko.util.ByteString]):

Scala
:  @@snip [Message.scala](/http-core/src/main/scala/org/apache/pekko/http/scaladsl/model/ws/Message.scala) { #message-model }

Java
:  @@snip [Message.scala](/http-core/src/main/scala/org/apache/pekko/http/javadsl/model/ws/Message.scala) { #message-model }

The data of a message is provided as a stream because WebSocket messages do not have a predefined size and could
(in theory) be infinitely long. However, only one message can be open per direction of the WebSocket connection,
so that many application level protocols will want to make use of the delineation into (small) messages to transport
single application-level data units like "one event" or "one chat message".

Many messages are small enough to be sent or received in one go. As an opportunity for optimization, the model provides
the notion of a "strict" message to represent cases where a whole message was received in one go.
@scala[Strict messages are represented with the `Strict` subclass for each kind of message which contains data as a strict, i.e. non-streamed, @apidoc[org.apache.pekko.util.ByteString] or `String`.]
@java[If `TextMessage.isStrict` returns true, the complete data is already available and can be accessed with `TextMessage.getStrictText` (analogously for @apidoc[BinaryMessage]).]

When receiving data from the network connection the WebSocket implementation tries to create a strict message whenever
possible, i.e. when the complete data was received in one chunk. However, the actual chunking of messages over a network
connection and through the various streaming abstraction layers is not deterministic from the perspective of the
application. Therefore, application code must be able to handle both streamed and strict messages and not expect
certain messages to be strict. (Particularly, note that tests against `localhost` will behave differently than tests
against remote peers where data is received over a physical network connection.)

For sending data, you can use @scala[`TextMessage.apply(text: String)`]@java[`TextMessage.create(String)`] to create a strict message if the
complete message has already been assembled. Otherwise, use @scala[`TextMessage.apply(textStream: Source[String, \_])`]@java[`TextMessage.create(Source<String, ?>)`]
to create a streaming message from an Apache Pekko Stream source.

## Routing support

To handle websocket requests, you can either use the directive described in this section, or
use the more low-level @ref[WebSocketUpgrade](#websocketupgrade) attribute described
in the next section.

The routing DSL provides the @ref[handleWebSocketMessages](../routing-dsl/directives/websocket-directives/handleWebSocketMessages.md) directive to install a WebSocket handler if a request
is a WebSocket request. Otherwise, the directive rejects the request.

Let's look at how the above example can be rewritten using the high-level routing DSL.

Instead of writing the request handler manually, the routing behavior of the app is defined by a route that
uses the @ref[handleWebSocketMessages](../routing-dsl/directives/websocket-directives/handleWebSocketMessages.md) directive:

Scala
:  @@snip [WebSocketDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/WebSocketDirectivesExamplesSpec.scala) { #greeter-service }

Java
:  @@snip [WebSocketRoutingExample.java](/docs/src/test/java/docs/http/javadsl/server/WebSocketRoutingExample.java) { #websocket-route }

The handling code itself will be the same as with using the low-level API.

@@@ div { .group-scala }
The example also includes code demonstrating the testkit support for WebSocket services. It allows to create WebSocket
requests to run against a route using *WS* which can be used to provide a mock WebSocket probe that allows manual
testing of the WebSocket handler's behavior if the request was accepted.
@@@

See the @github[full routing example](/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java).

## WebSocketUpgrade

To handle websocket requests, you can either use the @apidoc[WebSocketUpgrade] attribute directly, or
use the more high-level @ref[Routing Support](#routing-support) described above.

The entrypoint for the WebSocket API is the @apidoc[WebSocketUpgrade] attribute which is added to a request
if Apache Pekko HTTP encounters a WebSocket upgrade request.

The WebSocket specification mandates that details of the WebSocket connection are negotiated by placing special-purpose
HTTP headers into request and response of the HTTP upgrade. In Apache Pekko HTTP these HTTP-level details of the WebSocket
handshake are hidden from the application and don't need to be managed manually.

Instead, the @apidoc[WebSocketUpgrade] attribute represents a valid WebSocket upgrade request. An application can detect
a WebSocket upgrade request by looking for the @apidoc[WebSocketUpgrade] attribute. It can choose to accept the upgrade and
start a WebSocket connection by responding to that request with an @apidoc[HttpResponse] generated by one of the
`WebSocketUpgradeWebSocketUpgrade.handleMessagesWith` methods. In its most general form this method expects two arguments:
first, a handler @scala[@apidoc[Flow[Message, Message, Any]]]@java[@apidoc[Flow[Message, Message, ?]]] that will be used to handle WebSocket messages on this connection.
Second, the application can optionally choose one of the proposed application-level sub-protocols by inspecting the
values of @scala[`WebSocketUpgrade.requestedProtocols`]@java[`WebSocketUpgrade.getRequestedProtocols`] and pass the chosen protocol value to @scala[`handleMessages`]@java[`handleMessagesWith`].

### Handling Messages

A message handler is expected to be implemented as a @scala[@apidoc[Flow[Message, Message, Any]]]@java[@apidoc[Flow[Message, Message, ?]]]. For typical request-response
scenarios this fits very well and such a @apidoc[Flow] can be constructed from a simple function by using
@scala[`Flow[Message].map` or `Flow[Message].mapAsync`]@java[`Flow.<Message>create().map` or `Flow.<Message>create().mapAsync`].

There are other use-cases, e.g. in a server-push model, where a server message is sent spontaneously, or in a
true bi-directional scenario where input and output aren't logically connected. Providing the handler as a @apidoc[Flow] in
these cases may not fit. @scala[Another method named `WebSocketUpgrade.handleMessagesWithSinkSource`]@java[An overload of `WebSocketUpgrade.handleMessagesWith`] is provided, instead,
which allows to pass an output-generating @apidoc[Source[Message, \_]] and an input-receiving @apidoc[Sink[Message, \_]] independently.

Note that a handler is required to consume the data stream of each message to make place for new messages. Otherwise,
subsequent messages may be stuck and message traffic in this direction will stall.

### Example

Let's look at an @scala[@github[example](/docs/src/test/scala/docs/http/scaladsl/server/WebSocketExampleSpec.scala)]@java[@github[example](/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java)].

WebSocket requests come in like any other requests. In the example, requests to `/greeter` are expected to be
WebSocket requests:

Scala
:  @@snip [WebSocketExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/WebSocketExampleSpec.scala) { #websocket-request-handling }

Java
:  @@snip [WebSocketCoreExample.java](/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java) { #websocket-handling }

It @scala[uses pattern matching on]@java[looks at] the path and then inspects the request to query for the @apidoc[WebSocketUpgrade] attribute. If
such an attribute is found, it is used to generate a response by passing a handler for WebSocket messages to the
@scala[`handleMessages`]@java[`handleMessagesWith`] method. If no such attribute is found a `400 Bad Request` response is generated.

@@@ div { .group-java }
You can also use the @javadoc[WebSocket.handleWebSocketRequestWith](org.apache.pekko.http.javadsl.model.ws.WebSocket) helper method which can be used if
only WebSocket requests are expected. The method looks for the @apidoc[WebSocketUpgrade] attribute and returns a response
that will install the passed WebSocket handler if the header is found. If the request is no WebSocket request it will
return a `400 Bad Request` error response.
@@@

In the example, the passed handler expects text messages where each message is expected to contain a (person's) name
and then responds with another text message that contains a greeting:

Scala
:  @@snip [WebSocketExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/WebSocketExampleSpec.scala) { #websocket-handler }

Java
:  @@snip [WebSocketCoreExample.java](/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java) { #websocket-handler }

@@@ note
Inactive WebSocket connections will be dropped according to the @ref[idle-timeout settings](../common/timeouts.md#idle-timeouts).
In case you need to keep inactive connections alive, you can either tweak your idle-timeout or inject
'keep-alive' messages regularly.
@@@

<a id="keep-alive-ping"></a>

## Automatic keep-alive Ping support

For long running websocket connections it may be beneficial to enable automatic heartbeat using `Ping` frames.
Those are often used as a way to keep otherwise idle connections from being closed and also a way of ensuring the 
connection remains usable even after no data frames are communicated over a longer period of time. Such heartbeat may be 
initiated by either side of the connection, and the choice which side performs the heart beating is use-case dependent. 

This is supported in a transparent way via configuration in Apache Pekko HTTP, and you can enable it by setting the: 
`pekko.http.server.websocket.periodic-keep-alive-max-idle = 1 second` to a specified max idle timeout. The keep alive triggers
when no other messages are in-flight during the such configured period. Apache Pekko HTTP will then automatically send
a [`Ping` frame](https://tools.ietf.org/html/rfc6455#section-5.5.2) for each of such idle intervals.

By default, the automatic keep-alive feature is disabled.

### Custom keep-alive data payloads

By default, pings do not carry any payload, as it is often enough to simply push *any* frame over the connection
to ensure the connection stays healthy (or detect if it was severed), however you may configure them to carry a custom 
payload, to do this you can provide a function that will be asked to emit the payload for each of the ping messages generated:

Scala
:  @@snip [WebSocketExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/WebSocketExampleSpec.scala) { #websocket-ping-payload-server }

Java
:  @@snip [WebSocketCoreExample.java](/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java) { #websocket-ping-payload-server }

### Uni-directional Pong keep-alive


A Ping response will always be replied to by the client-side with an appropriate `Pong` reply, carrying the same payload.
It is also possible to configure the keep-alive mechanism to send `Pong` frames instead of `Ping` frames, 
which enables an [uni-directional heartbeat](https://tools.ietf.org/html/rfc6455#section-5.5.3) mechanism (in which case 
the client side will *not* reply to such heartbeat). You can configure this mode by setting: 
`pekko.http.server.websocket.periodic-keep-alive-mode = pong`.
