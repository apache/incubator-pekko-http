# Connection-Level Client-Side API

The connection-level API is the lowest-level client-side API Apache Pekko HTTP provides. It gives you full control over when
HTTP connections are opened and closed and how requests are to be sent across which connection. As such it offers the
highest flexibility at the cost of providing the least convenience.

@@@ note
It is recommended to first read the @ref[Implications of the streaming nature of Request/Response Entities](../implications-of-streaming-http-entity.md) section,
as it explains the underlying full-stack streaming concepts, which may be unexpected when coming
from a background with non-"streaming first" HTTP Clients.
@@@

## Opening HTTP Connections

With the connection-level API you open a new HTTP connection to a target endpoint by materializing a @apidoc[Flow]
returned by the builder returned by @scala[`Http().connectionTo(...)`]@java[`Http.get(system).connectionTo(...)`] method.
Here is an example:

Scala
:  @@snip [HttpClientOutgoingConnection.scala](/docs/src/test/scala/docs/http/scaladsl/HttpClientOutgoingConnection.scala)

Java
:  @@snip [HttpClientExampleDocTest.java](/docs/src/test/java/docs/http/javadsl/HttpClientExampleDocTest.java) { #outgoing-connection-example }

In addition to the host name and port the builder @apidoc[OutgoingConnectionBuilder] returned by @scala[`Http().connectionTo(...)`]@java[`Http.get(system).connectionTo(...)`]
method also allows you to specify additional properties and as the final step deciding which protocol to use 
(HTTP/1, HTTP/1 over TLS, HTTP/2 over TLS or HTTP/2 with prior knowledge over a plaintext connection). For details on 
using HTTP/2 see @ref[Client-Side HTTP/2](./http2.md).

No connection is attempted until the returned flow is actually materialized! If the flow is materialized
several times then several independent connections will be opened (one per materialization).
If the connection attempt fails, for whatever reason, the materialized flow will be immediately terminated with a
respective exception.

## Request-Response Cycle

Once the connection flow has been materialized it is ready to consume @apidoc[HttpRequest] instances from the source it is
attached to. Each request is sent across the connection and incoming responses dispatched to the downstream pipeline.
Of course and as always, back-pressure is adequately maintained across all parts of the
connection. This means that, if the downstream pipeline consuming the HTTP responses is slow, the request source will
eventually be slowed down in sending requests.

Any errors occurring on the underlying connection are surfaced as exceptions terminating the response stream (and
canceling the request source).

## Closing Connections

Apache Pekko HTTP actively closes an established connection upon reception of a response containing `Connection: close` header.
The connection can also be closed by the server.

An application can actively trigger the closing of the connection by completing the request stream. In this case the
underlying TCP connection will be closed when the last pending response has been received.

The connection will also be closed if the response entity is cancelled (e.g. by attaching it to `Sink.cancelled()`)
or consumed only partially (e.g. by using `take` combinator). In order to prevent this behaviour the entity should be
explicitly drained by attaching it to `Sink.ignore()`.

## Timeouts

Currently Apache Pekko HTTP doesn't implement client-side request timeout checking itself as this functionality can be regarded
as a more general purpose streaming infrastructure feature.

It should be noted that Apache Pekko Streams provide various timeout functionality so any API that uses streams can benefit
from the stream stages such as `idleTimeout`, `backpressureTimeout`, `completionTimeout`, `initialTimeout`
and `throttle`. To learn more about these refer to their documentation in Apache Pekko Streams.

For more details about timeout support in Apache Pekko HTTP in general refer to @ref[Apache Pekko HTTP Timeouts](../common/timeouts.md).

<a id="http-client-layer"></a>
## Stand-Alone HTTP Layer Usage

Due to its Reactive-Streams-based nature the Apache Pekko HTTP layer is fully detachable from the underlying TCP
interface. While in most applications this "feature" will not be crucial it can be useful in certain cases to be able
to "run" the HTTP layer (and, potentially, higher-layers) against data that do not come from the network but rather
some other source. Potential scenarios where this might be useful include tests, debugging or low-level event-sourcing
(e.g. by replaying network traffic).

On the client-side the stand-alone HTTP layer forms a `BidiStage` stage that "upgrades" a potentially encrypted raw connection to the HTTP level.
It is defined like this:

@@@ div { .group-scala }
@@snip [Http.scala](/http-core/src/main/scala/org/apache/pekko/http/scaladsl/Http.scala) { #client-layer }
@@@
@@@ div { .group-java }
```java
BidiFlow<HttpRequest, SslTlsOutbound, SslTlsInbound, HttpResponse, NotUsed>
```
@@@

You create an instance of @scala[`Http.ClientLayer`]@java[the layer] by calling one of the two overloads
of the @scala[`Http().clientLayer`]@java[`Http.get(system).clientLayer`] method,
which also allows for varying degrees of configuration.
