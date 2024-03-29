# authenticateBasic

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #Authenticator }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateBasic }

@@@

## Description

Wraps the inner route with Http Basic authentication support using a given @scala[@scaladoc[Authenticator[T]](org.apache.pekko.http.scaladsl.server.Directives#Authenticator[T]=org.apache.pekko.http.scaladsl.server.directives.Credentials=%3EOption[T])]@java[`Authenticator<T>` - function from `Optional<ProvidedCredentials>` to `Optional<T>`].

Provides support for handling [HTTP Basic Authentication](https://en.wikipedia.org/wiki/Basic_auth).

Given a function returning @scala[`Some[T]`]@java[an `Optional<T>` with a value] upon successful authentication and @scala[`None`]@java[an empty `Optional<T>`] otherwise,
respectively applies the inner route or rejects the request with a @apidoc[AuthenticationFailedRejection] rejection,
which by default is mapped to an `401 Unauthorized` response.

Longer-running authentication tasks (like looking up credentials in a database) should use the @ref[authenticateBasicAsync](authenticateBasicAsync.md)
variant of this directive which allows it to run without blocking routing layer of Apache Pekko HTTP, freeing it for other requests.

Standard HTTP-based authentication which uses the `WWW-Authenticate` header containing challenge data and
@apidoc[Authorization] header for receiving credentials is implemented in `authenticateOrRejectWithChallenge`.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

@@@ warning
Make sure to use basic authentication only over SSL/TLS because credentials are transferred in plaintext.
@@@

## Example

Scala
:  @@snip [SecurityDirectivesExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/directives/SecurityDirectivesExamplesSpec.scala) { #authenticateBasic-0 }

Java
:  @@snip [SecurityDirectivesExamplesTest.java](/docs/src/test/java/docs/http/javadsl/server/directives/SecurityDirectivesExamplesTest.java) { #authenticateBasic }
