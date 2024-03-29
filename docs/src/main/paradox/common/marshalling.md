# Marshalling

@java[TODO @github[overhaul for Java](#1367)]

Marshalling is the process of converting a higher-level (object) structure into some kind of lower-level
representation, often a "wire format". Other popular names for marshalling are "serialization" or "pickling".

In Apache Pekko HTTP, marshalling means the conversion of an object of type `T` into a lower-level target type,
e.g. a `MessageEntity` (which forms the "entity body" of an HTTP request or response) or a full @apidoc[HttpRequest] or
@apidoc[HttpResponse].

On the server-side, for example, marshalling is used to convert an application-domain object to a response entity. Requests can
contain an @apidoc[Accept] header that lists acceptable content types for the client, such as `application/json` and `application/xml`. A marshaller contains the logic to
negotiate the result content types based on the @apidoc[Accept] and the `AcceptCharset` headers.

## Basic Design

Marshalling of instances of type `A` into instances of type `B` is performed by a @apidoc[Marshaller[A, B]].

Contrary to what you might initially expect, @apidoc[Marshaller[A, B]] is not a plain function `A => B` but rather
essentially a function @scala[`A => Future[List[Marshalling[B]]]`]@java[`A => CompletionStage<List<Marshalling<B>>>`].
Let's dissect this rather complicated looking signature piece by piece to understand why marshallers are designed this
way.
Given an instance of type `A` a @apidoc[Marshaller[A, B]] produces:

1. A @scala[`Future`]@java[`CompletionStage`]: This is probably quite clear. Marshallers are not required to synchronously produce a result, so instead
they return a future, which allows for asynchronicity in the marshalling process.

2. of `List`: Rather than only a single target representation for `A` marshallers can offer several ones. Which
one will be rendered onto the wire in the end is decided by content negotiation.
For example, the @apidoc[Marshaller[OrderConfirmation, MessageEntity]] might offer a JSON as well as an XML representation.
The client can decide through the addition of an @apidoc[Accept] request header which one is preferred. If the client doesn't
express a preference the first representation is picked.

3. of @scala[`Marshalling[B]`]@java[`Marshalling<B>`]: Rather than returning an instance of `B` directly marshallers first produce a
@scala[`Marshalling[B]`]@java[`Marshalling<B>`]. This allows for querying the @apidoc[MediaType] and potentially the @apidoc[HttpCharset] that the marshaller
will produce before the actual marshalling is triggered. Apart from enabling content negotiation this design allows for
delaying the actual construction of the marshalling target instance to the very last moment when it is really needed.

@@@ div { .group-scala }

This is how `Marshalling` is defined:

@@snip [Marshaller.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/marshalling/Marshaller.scala) { #marshalling }

Apache Pekko HTTP also defines a number of helpful aliases for the types of marshallers that you'll likely work with most:

@@snip [package.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/marshalling/package.scala) { #marshaller-aliases }

@@@

## Predefined Marshallers

Apache Pekko HTTP already predefines a number of marshallers for the most common types.
Specifically these are:

@@@ div { .group-scala }

 * @scaladoc[PredefinedToEntityMarshallers](org.apache.pekko.http.scaladsl.marshalling.PredefinedToEntityMarshallers)
    * `Array[Byte]`
    * @apidoc[org.apache.pekko.util.ByteString]
    * `Array[Char]`
    * `String`
    * `org.apache.pekko.http.scaladsl.model.FormData`
    * `org.apache.pekko.http.scaladsl.model.MessageEntity`
    * `T <: org.apache.pekko.http.scaladsl.model.Multipart`
 * @scaladoc[PredefinedToResponseMarshallers](org.apache.pekko.http.scaladsl.marshalling.PredefinedToResponseMarshallers)
    * `T`, if a `ToEntityMarshaller[T]` is available
    * @apidoc[HttpResponse]
    * @apidoc[StatusCode]
    * `(StatusCode, T)`, if a `ToEntityMarshaller[T]` is available
    * `(Int, T)`, if a `ToEntityMarshaller[T]` is available
    * `(StatusCode, immutable.Seq[HttpHeader], T)`, if a `ToEntityMarshaller[T]` is available
    * `(Int, immutable.Seq[HttpHeader], T)`, if a `ToEntityMarshaller[T]` is available
 * @scaladoc[PredefinedToRequestMarshallers](org.apache.pekko.http.scaladsl.marshalling.PredefinedToRequestMarshallers)
    * @apidoc[HttpRequest]
    * @apidoc[Uri]
    * `(HttpMethod, Uri, T)`, if a `ToEntityMarshaller[T]` is available
    * `(HttpMethod, Uri, immutable.Seq[HttpHeader], T)`, if a `ToEntityMarshaller[T]` is available
 * @scaladoc[GenericMarshallers](org.apache.pekko.http.scaladsl.marshalling.GenericMarshallers)
    * @apidoc[Marshaller[Throwable, T]]
    * @apidoc[Marshaller[Option[A], B]], if a @apidoc[Marshaller[A, B]] and an `EmptyValue[B]` is available
    * @apidoc[Marshaller[Either[A1, A2], B]], if a @apidoc[Marshaller[A1, B]] and a @apidoc[Marshaller[A2, B]] is available
    * @apidoc[Marshaller[Future[A], B]], if a @apidoc[Marshaller[A, B]] is available
    * @apidoc[Marshaller[Try[A], B]], if a @apidoc[Marshaller[A, B]] is available

@@@

@@@ div { .group-java }

 * Predefined @apidoc[RequestEntity] marshallers:
    * `byte[]`
    * @apidoc[org.apache.pekko.util.ByteString]
    * `char[]`
    * `String`
    * @apidoc[FormData]
    * `Optional<T>` using an existing @apidoc[RequestEntity] marshaller for `T`. An empty optional will yield an empty entity.
 * Predefined @apidoc[HttpResponse] marshallers:
    * `T` using an existing @apidoc[RequestEntity] marshaller for `T`
    * `T` and @apidoc[StatusCode] using an existing @apidoc[RequestEntity] marshaller for `T`
    * `T`, @apidoc[StatusCode] and `Iterable[HttpHeader]` using an existing @apidoc[RequestEntity] marshaller for `T`

All marshallers can be found in @apidoc[Marshaller].

@@@

@@@ div { .group-scala }

## Implicit Resolution

The marshalling infrastructure of Apache Pekko HTTP relies on a type-class based approach, which means that @apidoc[Marshaller]
instances from a certain type `A` to a certain type `B` have to be available implicitly.

The implicits for most of the predefined marshallers in Apache Pekko HTTP are provided through the companion object of the
@apidoc[Marshaller] trait. This means that they are always available and never need to be explicitly imported.
Additionally, you can simply "override" them by bringing your own custom version into local scope.

@@@

## Custom Marshallers

Apache Pekko HTTP gives you a few convenience tools for constructing marshallers for your own types.
Before you do that you need to think about what kind of marshaller you want to create.
If all your marshaller needs to produce is a `MessageEntity` then you should probably provide a
@scala[`ToEntityMarshaller[T]`]@java[@apidoc[Marshaller[T, MessageEntity]]]. The advantage here is that it will work on both the client- as well as the server-side since
a @scala[`ToResponseMarshaller[T]`]@java[@apidoc[Marshaller[T, HttpResponse]]] as well as a @scala[`ToRequestMarshaller[T]`]@java[@apidoc[Marshaller[T, HttpRequest]]] can automatically be created if a
@scala[`ToEntityMarshaller[T]`]@java[@apidoc[Marshaller[T, MessageEntity]]] is available.

If, however, your marshaller also needs to set things like the response status code, the request method, the request URI
or any headers then a @scala[`ToEntityMarshaller[T]`]@java[@apidoc[Marshaller[T, MessageEntity]]] won't work. You'll need to fall down to providing a
@scala[`ToResponseMarshaller[T]`]@java[@apidoc[Marshaller[T, HttpResponse]]] or a @scala[`ToRequestMarshaller[T]]`]@java[@apidoc[Marshaller[T, HttpRequest]]] directly.

For writing your own marshallers you won't have to "manually" implement the @apidoc[Marshaller] @scala[trait]@java[class] directly.

@@@ div { .group-scala }

Rather, it should be possible to use one of the convenience construction helpers defined on the @apidoc[Marshaller]
companion:

@@snip [Marshaller.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/marshalling/Marshaller.scala) { #marshaller-creation }

@@@

## Deriving Marshallers

Sometimes you can save yourself some work by reusing existing marshallers for your custom ones.
The idea is to "wrap" an existing marshaller with some logic to "re-target" it to your type.

In this regard wrapping a marshaller can mean one or both of the following two things:

 * Transform the input before it reaches the wrapped marshaller
 * Transform the output of the wrapped marshaller

For the latter (transforming the output) you can use `baseMarshaller.map`, which works exactly as it does for functions.
For the former (transforming the input) you have four alternatives:

 * `baseMarshaller.compose`
 * `baseMarshaller.composeWithEC`
 * `baseMarshaller.wrap`
 * `baseMarshaller.wrapWithEC`

`compose` works just like it does for functions.
`wrap` is a compose that allows you to also change the `ContentType` that the marshaller marshals to.
The `...WithEC` variants allow you to receive an `ExecutionContext` internally if you need one, without having to
depend on one being available implicitly at the usage site.

## Using Marshallers

In many places throughout Apache Pekko HTTP, marshallers are used implicitly, e.g. when you define how to @ref[complete](../routing-dsl/directives/route-directives/complete.md) a
request using the @ref[Routing DSL](../routing-dsl/index.md).

@@@ div { .group-scala }

However, you can also use the marshalling infrastructure directly if you wish, which can be useful for example in tests.
The best entry point for this is the @scaladoc[Marshal](org.apache.pekko.http.scaladsl.marshalling.Marshal) object, which you can use like this:

@@snip [MarshalSpec.scala](/docs/src/test/scala/docs/http/scaladsl/MarshalSpec.scala) { #use-marshal }

@@@

@@@ div { .group-java }

However, many directives dealing with @ref[marshalling](../routing-dsl/directives/marshalling-directives/index.md) also  require that you pass a marshaller explicitly. The following example shows how to marshal Java bean classes to JSON using the @ref:[Jackson JSON support](json-support.md#jackson-support):

@@snip [PetStoreExample.java](/http-tests/src/main/java/org/apache/pekko/http/javadsl/server/examples/petstore/PetStoreExample.java) { #imports #marshall }

@@@
