# authenticateOAuth2Async

@@@ div { .group-scala }
## Signature

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #AsyncAuthenticator }

@@signature [SecurityDirectives.scala](/http/src/main/scala/org/apache/pekko/http/scaladsl/server/directives/SecurityDirectives.scala) { #authenticateOAuth2Async }

@@@

## Description

Wraps the inner route with OAuth Bearer Token authentication support using a given @scala[@scaladoc[AsyncAuthenticator[T]](org.apache.pekko.http.scaladsl.server.Directives#AsyncAuthenticator[T]=org.apache.pekko.http.scaladsl.server.directives.Credentials=%3Escala.concurrent.Future[Option[T]])]@java[ `AsyncAuthenticator<T>` - function from `Optional<ProvidedCredentials>` to `CompletionStage<Optional<T>>`].

Provides support for extracting the so-called "*Bearer Token*" from the @apidoc[Authorization] HTTP Header,
which is used to initiate an OAuth2 authorization.

@@@ warning
This directive does not implement the complete OAuth2 protocol, but instead enables implementing it,
by extracting the needed token from the HTTP headers.
@@@

Given a function returning @scala[`Some[T]`]@java[a non empty `Optional<T>`] upon successful authentication and @scala[`None`]@java[an empty `Optional<T>`] otherwise,
respectively applies the inner route or rejects the request with a @apidoc[AuthenticationFailedRejection] rejection,
which by default is mapped to an `401 Unauthorized` response.

See also @ref[authenticateOAuth2](authenticateOAuth2.md) if the authorization operation is rather quick, and does not have to execute asynchronously.

See @ref[Credentials and password timing attacks](index.md#credentials-and-timing-attacks) for details about verifying the secret.

For more information on how OAuth2 works see [RFC 6750](https://tools.ietf.org/html/rfc6750).

## Example

Usage in code is exactly the same as @ref[authenticateBasicAsync](authenticateBasicAsync.md),
with the difference that one must validate the token as OAuth2 dictates (which is currently not part of Apache Pekko HTTP itself).
