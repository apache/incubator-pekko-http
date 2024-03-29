# authenticateBasicAsync

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #AsyncAuthenticator }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateBasicAsync }

@@@

## Description

Wraps the inner route with Http Basic authentication support using a given @scala[@scaladoc[AsyncAuthenticator[T]](org.apache.pekko.http.scaladsl.server.Directives#AsyncAuthenticator[T]=org.apache.pekko.http.scaladsl.server.directives.Credentials=%3Escala.concurrent.Future[Option[T]])]@java[ `AsyncAuthenticator<T>` - function from `Optional<ProvidedCredentials>` to `CompletionStage<Optional<T>>`].

This variant of the @ref[authenticateBasic](authenticateBasic.md) directive returns a @scala[`Future[Option[T]]`]@java[`CompletionStage<Optional<T>>`] which allows freeing up the routing
layer of Apache Pekko HTTP, freeing it for other requests. It should be used whenever an authentication is expected to take
a longer amount of time (e.g. looking up the user in a database).

In case the returned option is @scala[`None`]@java[an empty `Optional`] the request is rejected with a @apidoc[AuthenticationFailedRejection],
which by default is mapped to an `401 Unauthorized` response.

Standard HTTP-based authentication which uses the `WWW-Authenticate` header containing challenge data and
@apidoc[Authorization] header for receiving credentials is implemented in `authenticateOrRejectWithChallenge`.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

@@@ warning
Make sure to use basic authentication only over SSL/TLS because credentials are transferred in plaintext.
@@@

## Example

Scala
:  @@snip [SecurityDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SecurityDirectivesExamplesSpec.scala) { #authenticateBasicAsync-0 }

Java
:  @@snip [SecurityDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SecurityDirectivesExamplesTest.java) { #authenticateBasicAsync }
