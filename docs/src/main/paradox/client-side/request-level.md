# Request-Level Client-Side API

The request-level API is the recommended and most convenient way of using Apache Pekko HTTP's client-side functionality. It internally builds upon the
@ref[Host-Level Client-Side API](host-level.md) to provide you with a simple and easy-to-use way of retrieving HTTP responses from remote servers.
Depending on your preference you can pick the [Future-based variant](#future-based-variant) or [Flow-based variant](#flow-based-variant).

@@@ note
It is recommended to first read the @ref[Implications of the streaming nature of Request/Response Entities](../implications-of-streaming-http-entity.md) section,
as it explains the underlying full-stack streaming concepts, which may be unexpected when coming
from a background with non-"streaming first" HTTP Clients.
@@@

@@@ note
The request-level API is implemented on top of a connection pool that is shared inside the actor system. A consequence of
using a pool is that long-running requests block a connection while running and starve other requests. Make sure not to use
the request-level API for long-running requests like long-polling GET requests. Use the @ref[Connection-Level Client-Side API](connection-level.md)
or an extra pool just for the long-running connection instead.
@@@

## Future-Based Variant

Most often, your HTTP client needs are very basic. You need the HTTP response for a certain request and don't
want to bother with setting up a full-blown streaming infrastructure.

For these cases Apache Pekko HTTP offers the @scala[`Http().singleRequest(...)`]@java[`Http.get(system).singleRequest(...)`] method, which turns an @apidoc[HttpRequest] instance
into @scala[`Future[HttpResponse]`]@java[`CompletionStage<HttpResponse>`]. Internally the request is dispatched across the (cached) host connection pool for the
request's effective URI.

The request must have either an absolute URI or a valid
`Host` header, otherwise the returned future will be completed with an error.

### Example

Scala
:   @@snip [HttpClientSingleRequest.scala](/docs/src/test/scala/docs/http/scaladsl/HttpClientSingleRequest.scala)

Java
:   @@snip [HttpClientExampleDocTest.java](/docs/src/test/java/docs/http/javadsl/HttpClientExampleDocTest.java) { #single-request-example }

### Using the Future-Based API in Actors

When using the @scala[`Future`]@java[`CompletionStage`] based API from inside a classic Apache Pekko @apidoc[Actor], all the usual caveats apply to how one should deal
with the futures completion. For example, you should not access the actor's state from within the @scala[`Future`]@java[`CompletionStage`]'s callbacks
(such as `map`, `onComplete`, ...) and, instead, you should use the @scala[`pipeTo`]@java[`pipe`] pattern to pipe the result back
to the actor as a message:

Scala
:   @@snip [HttpClientExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/HttpClientExampleSpec.scala) { #single-request-in-actor-example }

Java
:   @@snip [HttpClientExampleDocTest.java](/docs/src/test/java/docs/http/javadsl/HttpClientExampleDocTest.java) { #single-request-in-actor-example }

@@@ warning

Always make sure you consume the response entity streams (of type @scala[@apidoc[Source[ByteString,Unit]]]@java[@apidoc[Source[ByteString, Object]]]).
Connect the response entity `Source` to a @apidoc[Sink], or call @scala[`response.discardEntityBytes()`]@java[`response.discardEntityBytes(Materializer)`] 
if you don't care about the response entity. 

Read the @ref[Implications of the streaming nature of Request/Response Entities](../implications-of-streaming-http-entity.md) section for more details.

If the application doesn't subscribe to the response entity within 
`pekko.http.host-connection-pool.response-entity-subscription-timeout`, the stream will fail with a 
`TimeoutException: Response entity was not subscribed after ...`.
@@@

## Flow-Based Variant

The Flow-based variant of the request-level client-side API is presented by the @scala[`Http().superPool(...)`]@java[`Http.get(system).superPool(...)`] method.
It creates a new "super connection pool flow", which routes incoming requests to a (cached) host connection pool
depending on their respective effective URIs.

The @apidoc[Flow] returned by @scala[`Http().superPool(...)`]@java[`Http.get(system).superPool(...)`] is very similar to the one from the @ref[Host-Level Client-Side API](host-level.md), so the section on 
@ref[Using a Host Connection Pool](host-level.md#using-a-host-connection-pool) also applies here.

However, there is one notable difference between a "host connection pool client flow" for the Host-Level API and a
"super-pool flow" from the Request-Level API:

* In a "host connection pool client flow" the flow has an implicit target host context. Therefore, the requests it 
takes don't need to have absolute URIs or a valid `Host` header because the host connection pool will automatically 
add a `Host` header if required.

For a "super-pool flow" in the Request-Level API this is not the case. All requests to a super-pool must either 
have an absolute URI or a valid `Host` header, because otherwise it'd be impossible to find out which target endpoint 
to direct the request to.


## Collecting headers from a server response

Sometimes we would like to get only headers of specific type which are sent from a server. In order to collect headers in a type safe way Apache Pekko HTTP API provides a type for each HTTP header. Here is an example for getting all cookies set by a server (`Set-Cookie` header):

Scala
:   @@snip [HttpClientExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/HttpClientCollectingHeaders.scala)

Java
:   @@snip [HttpClientExampleDocTest.java](/docs/src/test/java/docs/http/javadsl/HttpClientExampleDocTest.java) { #collecting-headers-example }
