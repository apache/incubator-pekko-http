# HTTP Model

Apache Pekko HTTP model contains a deeply structured, fully immutable, case-class based model of all the major HTTP data
structures, like HTTP requests, responses and common headers.
It lives in the *pekko-http-core* module and forms the basis for most of Apache Pekko HTTP's APIs.

## Overview

Since pekko-http-core provides the central HTTP data structures you will find the following import in quite a
few places around the code base (and probably your own code as well):

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #import-model }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #import-model }

This brings all of the most relevant types in scope, mainly:

 * @apidoc[HttpRequest] and @apidoc[HttpResponse], the central message model
 * `headers`, the package containing all the predefined HTTP header models and supporting types
 * Supporting types like @apidoc[Uri], @apidoc[HttpMethods$], @apidoc[MediaTypes$], @apidoc[StatusCodes$], etc.

A common pattern is that the model of a certain entity is represented by an immutable type (class or trait),
while the actual instances of the entity defined by the HTTP spec live in an accompanying object carrying the name of
the type plus a trailing plural 's'.

For example:

 * Defined @apidoc[HttpMethod] instances @scala[live in]@java[are defined as static fields of] the @apidoc[HttpMethods$] @scala[object]@java[class].
 * Defined @apidoc[HttpCharset] instances @scala[live in]@java[are defined as static fields of] the @apidoc[HttpCharsets$] @scala[object]@java[class].
 * Defined @apidoc[HttpEncoding] instances @scala[live in]@java[are defined as static fields of] the @apidoc[HttpEncodings$] @scala[object]@java[class].
 * Defined @apidoc[HttpProtocol] instances @scala[live in]@java[are defined as static fields of] the @apidoc[HttpProtocols$] @scala[object]@java[class].
 * Defined @apidoc[MediaType] instances @scala[live in]@java[are defined as static fields of] the @apidoc[MediaTypes$] @scala[object]@java[class].
 * Defined @apidoc[StatusCode] instances @scala[live in]@java[are defined as static fields of] the @apidoc[StatusCodes$] @scala[object]@java[class].

## HttpRequest

@apidoc[HttpRequest] and @apidoc[HttpResponse] are the basic @scala[case]@java[immutable] classes representing HTTP messages.

An @apidoc[HttpRequest] consists of

 * a method (GET, POST, etc.)
 * a URI (see @ref[URI model](uri-model.md) for more information)
 * a seq of headers
 * an entity (body data)
 * a protocol

Here are some examples how to construct an @apidoc[HttpRequest]:

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #import-model #construct-request }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #import-model #construct-request }

@@@ div { .group-scala }
All parameters of `HttpRequest.apply` have default values set, so `headers` for example don't need to be specified
if there are none. Many of the parameters types (like @apidoc[HttpEntity] and @apidoc[Uri]) define implicit conversions
for common use cases to simplify the creation of request and response instances.
@@@
@@@ div { .group-java }
In its basic form `HttpRequest.create` creates an empty default GET request without headers which can then be
transformed using one of the `withX` methods, `addHeader`, or `addHeaders`. Each of those will create a
new immutable instance, so instances can be shared freely. There exist some overloads for `HttpRequest.create` that
simplify creating requests for common cases. Also, to aid readability, there are predefined alternatives for `create`
named after HTTP methods to create a request with a given method and URI directly.
@@@

@@@ note { title='String representation' }

There are certain environments where it is easy to inadvertently print, write or log entries built out of string representations of @apidoc[HttpRequest] instances. On the other hand, it is not uncommon for HTTP headers and entities to contain _Personal Identifying Information (PII)_ or _Sensitive Personal Information (SPI)_ . 

To avoid accidentally leaking such information, these fields are omitted from @apidoc[HttpRequest] `toString` output. 

If needed, it is possible to define a custom string representation including all fields as shown in the following example:

Scala
:   @@snip [HttpRequestDetailedStringExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/HttpRequestDetailedStringExampleSpec.scala) 

Java
:   @@snip [HttpRequestDetailedStringExampleTest.java](/docs/src/test/java/docs/http/javadsl/HttpRequestDetailedStringExampleTest.java) 

@@@

<a id="synthetic-headers"></a>
### Synthetic Headers

In some cases it may be necessary to deviate from fully RFC-Compliant behavior. For instance, Amazon S3 treats
the `+` character in the path part of the URL as a space, even though the RFC specifies that this behavior should
be limited exclusively to the query portion of the URI.

In order to work around these types of edge cases, Apache Pekko HTTP provides for the ability to provide extra,
non-standard information to the request via synthetic headers. These headers are not passed to the client
but are instead consumed by the request engine and used to override default behavior.

For instance, in order to provide a raw request URI, bypassing the default URL normalization, you could do the
following:

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #synthetic-header-s3 }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #synthetic-header-s3 }

## HttpResponse

An @apidoc[HttpResponse] consists of

 * a status code
 * a @scala[`Seq`]@java[list] of headers
 * an entity (body data)
 * a protocol

Here are some examples how to construct an @apidoc[HttpResponse]:

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #construct-response }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #construct-response }

In addition to the simple @scala[@apidoc[HttpEntity] constructors]@java[`HttpEntities.create` methods] which create an entity from a fixed `String` or @apidoc[org.apache.pekko.util.ByteString]
as shown here the Apache Pekko HTTP model defines a number of subclasses of @apidoc[HttpEntity] which allow body data to be specified as a
stream of bytes. @java[All of these types can be created using the method on `HttpEntites`.]

@@@ note { title='String representation' }

There are certain environments where it is easy to inadvertently print, write or log entries built out of string representations of @apidoc[HttpResponse] instances. On the other hand, it is not uncommon for HTTP headers and entities to contain _Personal Identifying Information (PII)_ or _Sensitive Personal Information (SPI)_ . 

To avoid accidentally leaking such information, these fields are omitted from @apidoc[HttpResponse] `toString` output. 

If needed, it is possible to define a custom string representation including all fields as shown in the following example:

Scala
:   @@snip [HttpResponseDetailedStringExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/HttpResponseDetailedStringExampleSpec.scala) 

Java
:   @@snip [HttpResponseDetailedStringExampleTest.java](/docs/src/test/java/docs/http/javadsl/HttpResponseDetailedStringExampleTest.java) 

@@@

<a id="httpentity"></a>
## HttpEntity

An @apidoc[HttpEntity] carries the data bytes of a message together with its Content-Type and, if known, its Content-Length.
In Apache Pekko HTTP, there are five different kinds of entities which model the various ways that message content can be
received or sent:

@scala[HttpEntity.Strict]@java[HttpEntityStrict]
: The simplest entity, which is used when all the entity are already available in memory.
It wraps a plain @apidoc[org.apache.pekko.util.ByteString] and represents a standard, unchunked entity with a known `Content-Length`.

@scala[HttpEntity.Default]@java[HttpEntityDefault]
: The general, unchunked HTTP/1.1 message entity.
It has a known length and presents its data as a @apidoc[Source[ByteString, \_]] which can be only materialized once.
It is an error if the provided source doesn't produce exactly as many bytes as specified.
The distinction of @scala[`Strict`]@java[`HttpEntityStrict`] and @scala[`Default`]@java[`HttpEntityDefault`] is an API-only one. On the wire,
both kinds of entities look the same.

@scala[HttpEntity.Chunked]@java[HttpEntityChunked]
: The model for HTTP/1.1 [chunked content](https://tools.ietf.org/html/rfc7230#section-4.1) (i.e. sent with `Transfer-Encoding: chunked`).
The content length is unknown and the individual chunks are presented as a @scala[`Source[HttpEntity.ChunkStreamPart]`]@java[@apidoc[Source[ChunkStreamPart, ?]]].
A `ChunkStreamPart` is either a non-empty @scala[`Chunk`]@java[chunk] or @scala[a `LastChunk`]@java[the empty last chunk] containing optional trailer headers.
The stream consists of zero or more @scala[`Chunked`]@java[non-empty chunks] parts and can be terminated by an optional @scala[`LastChunk` part]@java[last chunk].

@scala[HttpEntity.CloseDelimited]@java[HttpEntityCloseDelimited]
: An unchunked entity of unknown length that is implicitly delimited by closing the connection (`Connection: close`).
The content data are presented as a @apidoc[Source[ByteString, \_]].
Since the connection must be closed after sending an entity of this type it can only be used on the server-side for
sending a response.
Also, the main purpose of `CloseDelimited` entities is compatibility with HTTP/1.0 peers, which do not support
chunked transfer encoding. If you are building a new application and are not constrained by legacy requirements you
shouldn't rely on `CloseDelimited` entities, since implicit terminate-by-connection-close is not a robust way of
signaling response end, especially in the presence of proxies. Additionally this type of entity prevents connection
reuse which can seriously degrade performance. Use @scala[`HttpEntity.Chunked`]@java[`HttpEntityChunked`] instead!

@scala[HttpEntity.IndefiniteLength]@java[HttpEntityIndefiniteLength]
: A streaming entity of unspecified length for use in a `Multipart.BodyPart`.


Entity types @scala[`Strict`]@java[`HttpEntityStrict`], @scala[`Default`]@java[`HttpEntityDefault`], and @scala[`Chunked`]@java[`HttpEntityChunked`] are a subtype of @scala[`HttpEntity.Regular`]@java[@apidoc[RequestEntity]]
which allows to use them for requests and responses. In contrast, @scala[`HttpEntity.CloseDelimited`]@java[`HttpEntityCloseDelimited`] can only be used for responses.

Streaming entity types (i.e. all but @scala[`Strict`]@java[`HttpEntityStrict`]) cannot be shared or serialized. To create a strict, shareable copy of an
entity or message use `HttpEntity.toStrict` or `HttpMessage.toStrict` which returns a @scala[`Future`]@java[`CompletionStage`] of the object with
the body data collected into a @apidoc[org.apache.pekko.util.ByteString].

The @scala[@apidoc[HttpEntity] companion object]@java[class `HttpEntities`] contains @scala[several helper constructors]@java[static methods] to create entities from common types easily.

You can @scala[pattern match over]@java[use] the @scala[subtypes]@java[`isX` methods] of @apidoc[HttpEntity] @java[to find out of which subclass an entity is] if you want to provide
special handling for each of the subtypes. However, in many cases a recipient of an @apidoc[HttpEntity] doesn't care about
of which subtype an entity is (and how data is transported exactly on the HTTP layer). Therefore, the general method
@scala[`HttpEntity.dataBytes`]@java[`HttpEntity.getDataBytes()`] is provided which returns a @apidoc[Source[ByteString, \_]] that allows access to the data of an
entity regardless of its concrete subtype.

@@@ note { title='When to use which subtype?' }

 * Use @scala[`Strict`]@java[`HttpEntityStrict`] if the amount of data is "small" and already available in memory (e.g. as a `String` or @apidoc[org.apache.pekko.util.ByteString])
 * Use @scala[`Default`]@java[`HttpEntityDefault`] if the data is generated by a streaming data source and the size of the data is known
 * Use @scala[`Chunked`]@java[`HttpEntityChunked`] for an entity of unknown length
 * Use @scala[`CloseDelimited`]@java[`HttpEntityCloseDelimited`] for a response as a legacy alternative to @scala[`Chunked`]@java[`HttpEntityChunked`] if the client
doesn't support chunked transfer encoding. Otherwise use @scala[`Chunked`]@java[`HttpEntityChunked`]!
 * In a `Multipart.BodyPart` use @scala[`IndefiniteLength`]@java[`HttpEntityIndefiniteLength`] for content of unknown length.

@@@

@@@ warning { title="Caution" }

When you receive a non-strict message from a connection then additional data are only read from the network when you
request them by consuming the entity data stream. This means that, if you *don't* consume the entity stream then the
connection will effectively be stalled. In particular no subsequent message (request or response) will be read from
the connection as the entity of the current message "blocks" the stream.
Therefore you must make sure that you always consume the entity data, even in the case that you are not actually
interested in it!

@@@

### Limiting message entity length

All message entities that Apache Pekko HTTP reads from the network automatically get a length verification check attached to
them. This check makes sure that the total entity size is less than or equal to the configured
`max-content-length` <a id="^1" href="#1">[1]</a>, which is an important defense against certain Denial-of-Service attacks.
However, a single global limit for all requests (or responses) is often too inflexible for applications that need to
allow large limits for *some* requests (or responses) but want to clamp down on all messages not belonging into that
group.

In order to give you maximum flexibility in defining entity size limits according to your needs the @apidoc[HttpEntity]
features a `withSizeLimit` method, which lets you adjust the globally configured maximum size for this particular
entity, be it to increase or decrease any previously set value.
This means that your application will receive all requests (or responses) from the HTTP layer, even the ones whose
`Content-Length` exceeds the configured limit (because you might want to increase the limit yourself).
Only when the actual data stream @apidoc[Source] contained in the entity is materialized will the boundary checks be
actually applied. In case the length verification fails the respective stream will be terminated with an
`EntityStreamSizeException` either directly at materialization time (if the `Content-Length` is known) or whenever more
data bytes than allowed have been read.

When called on `Strict` entities the `withSizeLimit` method will return the entity itself if the length is within
the bound, otherwise a `Default` entity with a single element data stream. This allows for potential refinement of the
entity size limit at a later point (before materialization of the data stream).

By default all message entities produced by the HTTP layer automatically carry the limit that is defined in the
application's `max-content-length` config setting. If the entity is transformed in a way that changes the
content-length and then another limit is applied then this new limit will be evaluated against the new
content-length. If the entity is transformed in a way that changes the content-length and no new limit is applied
then the previous limit will be applied against the previous content-length.
Generally this behavior should be in line with your expectations.

> <a id="1" href="#^1">[1]</a> *pekko.http.parsing.max-content-length* (applying to server- as well as client-side),
*pekko.http.server.parsing.max-content-length* (server-side only),
*pekko.http.client.parsing.max-content-length* (client-side only) or
*pekko.http.host-connection-pool.client.parsing.max-content-length* (only host-connection-pools)

### Special processing for HEAD requests

[RFC 7230](https://tools.ietf.org/html/rfc7230#section-3.3.3) defines very clear rules for the entity length of HTTP messages.

Especially this rule requires special treatment in Apache Pekko HTTP:

>
Any response to a HEAD request and any response with a 1xx
(Informational), 204 (No Content), or 304 (Not Modified) status
code is always terminated by the first empty line after the
header fields, regardless of the header fields present in the
message, and thus cannot contain a message body.

Responses to HEAD requests introduce the complexity that *Content-Length* or *Transfer-Encoding* headers
can be present but the entity is empty. This is modeled by allowing @scala[*HttpEntity.Default*]@java[*HttpEntityDefault*] and @scala[*HttpEntity.Chunked*]@java[*HttpEntityChunked*]
to be used for HEAD responses with an empty data stream.

Also, when a HEAD response has an @scala[*HttpEntity.CloseDelimited*]@java[*HttpEntityCloseDelimited*] entity the Apache Pekko HTTP implementation will *not* close the
connection after the response has been sent. This allows the sending of HEAD responses without *Content-Length*
header across persistent HTTP connections.

<a id="header-model"></a>
## Header Model

Apache Pekko HTTP contains a rich model of the most common HTTP headers. Parsing and rendering is done automatically so that
applications don't need to care for the actual syntax of headers. Headers not modelled explicitly are represented
as a @apidoc[RawHeader], which is essentially a String/String name/value pair.

See these examples of how to deal with headers:

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #headers }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #headers }

## HTTP Headers

When the Apache Pekko HTTP server receives an HTTP request it tries to parse all its headers into their respective
model classes. Independently of whether this succeeds or not, the HTTP layer will
always pass on all received headers to the application. Unknown headers as well as ones with invalid syntax (according
to the header parser) will be made available as @apidoc[RawHeader] instances. For the ones exhibiting parsing errors a
warning message is logged depending on the value of the `illegal-header-warnings` config setting.

Some headers have special status in HTTP and are therefore treated differently from "regular" headers:

Content-Type
: The Content-Type of an HTTP message is modeled as the `contentType` field of the @apidoc[HttpEntity].
The `Content-Type` header therefore doesn't appear in the `headers` sequence of a message.
Also, a `Content-Type` header instance that is explicitly added to the `headers` of a request or response will
not be rendered onto the wire and trigger a warning being logged instead!

Transfer-Encoding
: Messages with `Transfer-Encoding: chunked` are represented @scala[via the `HttpEntity.Chunked`]@java[as a `HttpEntityChunked`] entity.
As such chunked messages that do not have another deeper nested transfer encoding will not have a `Transfer-Encoding`
header in their `headers` @scala[sequence]@java[list].
Similarly, a `Transfer-Encoding` header instance that is explicitly added to the `headers` of a request or
response will not be rendered onto the wire and trigger a warning being logged instead!

Content-Length
: The content length of a message is modelled via its [HttpEntity](#httpentity). As such no `Content-Length` header will ever
be part of a message's `header` sequence.
Similarly, a `Content-Length` header instance that is explicitly added to the `headers` of a request or
response will not be rendered onto the wire and trigger a warning being logged instead!

Server
: A `Server` header is usually added automatically to any response and its value can be configured via the
`pekko.http.server.server-header` setting. Additionally an application can override the configured header with a
custom one by adding it to the response's `header` sequence.

User-Agent
: A `User-Agent` header is usually added automatically to any request and its value can be configured via the
`pekko.http.client.user-agent-header` setting. Additionally an application can override the configured header with a
custom one by adding it to the request's `header` sequence.

Date
: The @apidoc[Date] response header is added automatically but can be overridden by supplying it manually.

Connection
: On the server-side Apache Pekko HTTP watches for explicitly added `Connection: close` response headers and as such honors
the potential wish of the application to close the connection after the respective response has been sent out.
The actual logic for determining whether to close the connection is quite involved. It takes into account the
request's method, protocol and potential @apidoc[Connection] header as well as the response's protocol, entity and
potential @apidoc[Connection] header. See @github[this test](/http-core/src/test/scala/org/apache/pekko/http/impl/engine/rendering/ResponseRendererSpec.scala) { #connection-header-table } for a full table of what happens when.

Strict-Transport-Security
: HTTP Strict Transport Security (HSTS) is a web security policy mechanism which is communicated by the
`Strict-Transport-Security` header. The most important security vulnerability that HSTS can fix is SSL-stripping
man-in-the-middle attacks. The SSL-stripping attack works by transparently converting a secure HTTPS connection into a
plain HTTP connection. The user can see that the connection is insecure, but crucially there is no way of knowing
whether the connection should be secure. HSTS addresses this problem by informing the browser that connections to the
site should always use TLS/SSL. See also [RFC 6797](https://tools.ietf.org/html/rfc6797).


<a id="custom-headers"></a>
## Custom Headers

Sometimes you may need to model a custom header type which is not part of HTTP and still be able to use it
as convenient as is possible with the built-in types.

Because of the number of ways one may interact with headers (i.e. try to @scala[match]@java[convert] a @apidoc[CustomHeader] @scala[against]@java[to] a @apidoc[RawHeader]
or the other way around etc), a helper @scala[trait]@java[classes] for custom Header types @scala[and their companions classes ]are provided by Apache Pekko HTTP.
Thanks to extending @apidoc[ModeledCustomHeader] instead of the plain @apidoc[CustomHeader] @scala[such header can be matched]@java[the following methods are at your disposal]:

Scala
:   @@snip [ModeledCustomHeaderSpec.scala](/http-tests/src/test/scala/org/apache/pekko/http/scaladsl/server/ModeledCustomHeaderSpec.scala) { #modeled-api-key-custom-header }

Java
:   @@snip [CustomHeaderExampleTest.java](/docs/src/test/java/docs/http/javadsl/CustomHeaderExampleTest.java) { #modeled-api-key-custom-header }

Which allows this @scala[CustomHeader]@java[modeled custom header] to be used in the following scenarios:

Scala
:   @@snip [ModeledCustomHeaderSpec.scala](/http-tests/src/test/scala/org/apache/pekko/http/scaladsl/server/ModeledCustomHeaderSpec.scala) { #matching-examples }

Java
:   @@snip [CustomHeaderExampleTest.java](/docs/src/test/java/docs/http/javadsl/CustomHeaderExampleTest.java) { #conversion-creation-custom-header }

Including usage within the header directives like in the following @ref[headerValuePF](../routing-dsl/directives/header-directives/headerValuePF.md) example:

Scala
:   @@snip [ModeledCustomHeaderSpec.scala](/http-tests/src/test/scala/org/apache/pekko/http/scaladsl/server/ModeledCustomHeaderSpec.scala) { #matching-in-routes }

Java
:   @@snip [CustomHeaderExampleTest.java](/docs/src/test/java/docs/http/javadsl/CustomHeaderExampleTest.java) { #header-value-pf }

@@@ note { .group-scala }
When defining custom headers, it is better to extend @apidoc[ModeledCustomHeader] instead of its parent @apidoc[CustomHeader].
Custom headers that extend @apidoc[ModeledCustomHeader] automatically comply with the pattern matching semantics that usually apply to built-in
types (such as matching a custom header against a @apidoc[RawHeader] in routing layers of Apache Pekko HTTP applications).
@@@

@@@ note { .group-java }
Implement @apidoc[ModeledCustomHeader] and @java[@javadoc[ModeledCustomHeaderFactory](org.apache.pekko.http.javadsl.model.headers.ModeledCustomHeaderFactory)] instead of @apidoc[CustomHeader] to be
able to use the convenience methods that allow parsing the custom user-defined header from @apidoc[HttpHeader].
@@@

## Attributes

Sometimes it can be useful to keep track of some information associated with a request without
explicitly closing over it. Such information can be attached to a request or response though
message attributes:

Scala
:   @@snip [ModelSpec.scala](/docs/src/test/scala/docs/http/scaladsl/ModelSpec.scala) { #attributes }

Java
:   @@snip [ModelDocTest.java](/docs/src/test/java/docs/http/javadsl/ModelDocTest.java) { #attributes }

Message attributes are only to be used within in your application, they are not present on the wire.

## Parsing / Rendering

Parsing and rendering of HTTP data structures is heavily optimized and for most types there's currently no public API
provided to parse (or render to) Strings or byte arrays.

@@@ note
Various parsing and rendering settings are available to tweak in the configuration under `pekko.http.client[.parsing]`,
`pekko.http.server[.parsing]` and `pekko.http.host-connection-pool[.client.parsing]`, with defaults for all of these
being defined in the `pekko.http.parsing` configuration section.

For example, if you want to change a parsing setting for all components, you can set the `pekko.http.parsing.illegal-header-warnings = off`
value. However this setting can be still overridden by the more specific sections, like for example `pekko.http.server.parsing.illegal-header-warnings = on`.

In this case both `client` and `host-connection-pool` APIs will see the setting `off`, however the server will see `on`.

In the case of `pekko.http.host-connection-pool.client` settings, they default to settings set in `pekko.http.client`,
and can override them if needed. This is useful, since both `client` and `host-connection-pool` APIs,
such as the Client API @scala[`Http().outgoingConnection`]@java[`Http.get(sys).outgoingConnection`] or the Host Connection Pool APIs @scala[`Http().singleRequest`]@java[`Http.get(sys).singleRequest`]
or @scala[`Http().superPool`]@java[`Http.get(sys).superPool`], usually need the same settings, however the `server` most likely has a very different set of settings.
@@@

<a id="registeringcustommediatypes"></a>
## Registering Custom Media Types

Apache Pekko HTTP @scala[@scaladoc[predefines](org.apache.pekko.http.scaladsl.model.MediaTypes$)]@java[@javadoc[predefines](org.apache.pekko.http.javadsl.model.MediaTypes)] most commonly encountered media types and emits them in their well-typed form while parsing http messages.
Sometimes you may want to define a custom media type and inform the parser infrastructure about how to handle these custom
media types, e.g. that `application/custom` is to be treated as `NonBinary` with `WithFixedCharset`. To achieve this you
need to register the custom media type in the server's settings by configuring @apidoc[ParserSettings] like this:

Scala
:   @@snip [CustomMediaTypesSpec.scala](/http-tests/src/test/scala/org/apache/pekko/http/scaladsl/CustomMediaTypesSpec.scala) { #application-custom }

Java
:   @@snip [CustomMediaTypesExampleTest.java](/docs/src/test/java/docs/http/javadsl/CustomMediaTypesExampleTest.java) { #application-custom-java }

You may also want to read about MediaType [Registration trees](https://en.wikipedia.org/wiki/Media_type#Registration_trees), in order to register your vendor specific media types
in the right style / place.

<a id="registeringcustomstatuscodes"></a>
## Registering Custom Status Codes

Similarly to media types, Apache Pekko HTTP @scala[@scaladoc:[predefines](org.apache.pekko.http.scaladsl.model.StatusCodes$)]@java[@javadoc:[predefines](org.apache.pekko.http.javadsl.model.StatusCodes)]
well-known status codes, however sometimes you may need to use a custom one (or are forced to use an API which returns custom status codes).
Similarly to the media types registration, you can register custom status codes by configuring @apidoc[ParserSettings] like this:

Scala
:   @@snip [CustomStatusCodesSpec.scala](/http-tests/src/test/scala/org/apache/pekko/http/scaladsl/CustomStatusCodesSpec.scala) { #application-custom }

Java
:   @@snip [CustomStatusCodesExampleTest.java](/docs/src/test/java/docs/http/javadsl/CustomStatusCodesExampleTest.java) { #application-custom-java }

<a id="registeringcustommethod"></a>
## Registering Custom HTTP Method

Apache Pekko HTTP also allows you to define custom HTTP methods, other than the well-known methods @scala[@scaladoc[predefined](org.apache.pekko.http.scaladsl.model.HttpMethods$)]@java[@javadoc[predefined](org.apache.pekko.http.javadsl.model.HttpMethods)] in Apache Pekko HTTP.
To use a custom HTTP method, you need to define it, and then add it to parser settings like below:

Scala
:   @@snip [CustomHttpMethodSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/CustomHttpMethodSpec.scala) { #application-custom }

Java
:   @@snip [CustomHttpMethodsExampleTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/CustomHttpMethodExamplesTest.java) { #customHttpMethod }
