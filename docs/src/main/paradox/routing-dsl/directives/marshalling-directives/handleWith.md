# handleWith

@@@ div { .group-scala }

## Signature

@@signature [MarshallingDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/MarshallingDirectives.scala) { #handleWith }

@@@

## Description

Completes the request using the given function. The input to the function is produced with
the in-scope entity unmarshaller and the result value of the function is marshalled with
the in-scope marshaller.  `handleWith` can be a convenient method combining `entity` with
`complete`.

The `handleWith` directive is used when you want to handle a route with a given function of
type @scala[A => B]@java[Function<A,B>].  `handleWith` will use both an in-scope unmarshaller to convert a request into 
type A and an in-scope marshaller to convert type B into a response. This is helpful when your 
core business logic resides in some other class or you want your business logic to be independent
of the REST interface written with pekko-http. You can use `handleWith` to "hand off" processing
to a given function without requiring any pekko-http-specific functionality.

`handleWith` is similar to `produce`.  The main difference is `handleWith` automatically
calls `complete` when the function passed to `handleWith` returns. Using `produce` you
must explicitly call the completion function passed from the `produce` function.

See @ref[marshalling](../../../common/marshalling.md) and @ref[unmarshalling](../../../common/unmarshalling.md) for guidance
on marshalling entities with pekko-http.

## Examples

The following example uses an `updatePerson` function with a `Person` @scala[case ]class as an input and output. We plug this function into our route using `handleWith`.

Scala
:   @@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #person-case-class }

Java
:   @@snip [MarshallingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MarshallingDirectivesExamplesTest.java) { #person }


Scala
:   @@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #example-handleWith-with-json }

Java
:   @@snip [MarshallingDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/MarshallingDirectivesExamplesTest.java) { #example-handleWith-with-json }


The @scala[PersonJsonSupport object handles]@java[previous example uses also @ref[Json Support via Jackson](../../../common/json-support.md#jackson-support) to handle] both marshalling and unmarshalling of the Person case class.

@@@ div { .group-scala }
@@snip [MarshallingDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/MarshallingDirectivesExamplesSpec.scala) { #person-json-support }

@@@
