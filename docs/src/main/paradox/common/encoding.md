# Encoding / Decoding

The [HTTP spec](https://tools.ietf.org/html/rfc7231#section-3.1.2.1) defines a `Content-Encoding` header, which signifies whether the entity body of an HTTP message is
"encoded" and, if so, by which algorithm. The only commonly used content encodings are compression algorithms.

Currently, Apache Pekko HTTP supports the compression and decompression of HTTP requests and responses with the `gzip` or
`deflate` encodings.
The core logic for this lives in the @scala[@scaladoc[org.apache.pekko.http.scaladsl.coding](org.apache.pekko.http.scaladsl.coding.index) package.]@java[@javadoc[org.apache.pekko.http.javadsl.coding.Coder](org.apache.pekko.http.javadsl.coding.Coder) enum class.]

## Server side

The support is not enabled automatically, but must be explicitly requested.
For enabling message encoding/decoding with @ref[Routing DSL](../routing-dsl/index.md) see the @ref[CodingDirectives](../routing-dsl/directives/coding-directives/index.md).

Usually, it suffices to surround routes that should support response encoding by the @ref[encodeResponse](../routing-dsl/directives/coding-directives/encodeResponse.md) directive,
and routes that should support request decoding by the @ref[decodeRequest](../routing-dsl/directives/coding-directives/decodeRequest.md) directive. Those directives will automatically
and transparently enable support for encodings and negotiating which encoding to use out of the default encodings supported. The set of predefined @apidoc[MediaTypes$] contains
a hint whether content of a given media type would benefit from compression.

## Client side

There is currently no high-level or automatic support for decoding responses on the client-side.

The following example shows how to decode responses manually based on the `Content-Encoding` header:

Scala
:   @@snip [HttpClientDecodingExampleSpec.scala](/docs/src/test/scala/docs/http/scaladsl/HttpClientDecodingExampleSpec.scala) { #single-request-decoding-example }

Java
:   @@snip [HttpClientDecodingExampleTest.java](/docs/src/test/java/docs/http/javadsl/HttpClientDecodingExampleTest.java) { #single-request-decoding-example }
